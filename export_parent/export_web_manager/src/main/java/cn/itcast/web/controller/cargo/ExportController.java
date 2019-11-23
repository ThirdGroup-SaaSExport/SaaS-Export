package cn.itcast.web.controller.cargo;

import cn.itcast.common.utils.BeanMapUtils;
import cn.itcast.domain.cargo.*;
import cn.itcast.domain.company.Company;
import cn.itcast.domain.vo.ExportProductVo;
import cn.itcast.domain.vo.ExportResult;
import cn.itcast.domain.vo.ExportVo;
import cn.itcast.service.cargo.ContractService;
import cn.itcast.service.cargo.ExportProductService;
import cn.itcast.service.cargo.ExportService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/cargo/export")
public class ExportController extends BaseController{

    @Reference
    private ExportService exportService;

    @Reference
    private ExportProductService exportProductService;

    @Reference
    private ContractService contractService;


    @RequestMapping(value = "/contractList")
    public String contractList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){

        ContractExample example = new ContractExample();
        ContractExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(1);
        criteria.andCompanyIdEqualTo(companyId);
        PageInfo pageInfo = contractService.findAll(example, page, size);
        request.setAttribute("page", pageInfo);

        return "cargo/export/export-contractList";
    }


    @RequestMapping(value = "/list")
    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
        ExportExample exportExample = new ExportExample();
        ExportExample.Criteria criteria = exportExample.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        PageInfo pageInfo = exportService.findAll(exportExample, page, size);
        request.setAttribute("page", pageInfo);
        return "cargo/export/export-list";
    }

    // id --> contractIds
    // 合同的数组
    @RequestMapping(value = "/toExport")
    public String toExport(String id){
        request.setAttribute("id", id);
        return "cargo/export/export-toExport";
    }


    @RequestMapping("/edit")
    public String edit(Export export,String contractIds){
        export.setContractIds(contractIds);
        export.setCompanyId(companyId);
        export.setCompanyName(companyName);

        if (export.getId().isEmpty()){
            //调用exportService保存
            exportService.save(export);
        }else {
            exportService.update(export);
        }
        return "redirect:/cargo/export/list.do";
    }

    @RequestMapping("/toView")
    public String toView(String id){
        Export export = exportService.findById(id);
        request.setAttribute("export",export);
        return "cargo/export/export-view";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id){
        Export export = exportService.findById(id);
        request.setAttribute("export",export);

        ExportProductExample example = new ExportProductExample();
        ExportProductExample.Criteria criteria = example.createCriteria();
        criteria.andExportIdEqualTo(id);
        List<ExportProduct> list = exportProductService.findAll(example);
        request.setAttribute("eps",list);
        return "cargo/export/export-update";
    }

    @RequestMapping("/submit")
    public String submit(String id){
        Export export = exportService.findById(id);
        //设置报运状态
        export.setState(1);
        //更新到数据库
        exportService.update(export);

        return "redirect:/cargo/export/list.do";
    }

    @RequestMapping("/cancel")
    public String cancel(String id){
        Export export = exportService.findById(id);
        //设置报运状态
        export.setState(0);
        //更新到数据库
        exportService.update(export);

        return "redirect:/cargo/export/list.do";
    }

    @RequestMapping("/delete")
    public String delete(String id){
        System.out.println("-------------------");
        exportService.delete(id);
        return "redirect:/cargo/export/list.do";
    }

    @RequestMapping("/exportE")
    public String exportE(String id){
        //通过id查询到报运单主表
        Export export = exportService.findById(id);
        //new一个海关提供的报运单实体类
        ExportVo exportVo = new ExportVo();
        //通过BeanUtils将报运单数据复制到海关提供的实体类中
        BeanUtils.copyProperties(export,exportVo);
        exportVo.setExportId(id);
        //通过id查询报运商品list
        ExportProductExample example = new ExportProductExample();
        ExportProductExample.Criteria criteria = example.createCriteria();
        criteria.andExportIdEqualTo(id);
        List<ExportProduct> exportProductList = exportProductService.findAll(example);
        List<ExportProductVo> epVoList = new ArrayList<ExportProductVo>();

        //循环list,new一个海关提供的商品实体类,将数据复制给它
        for (ExportProduct exportProduct : exportProductList) {
            ExportProductVo exportProductVo = new ExportProductVo();
            BeanUtils.copyProperties(exportProduct,exportProductVo);
            exportProductVo.setExportProductId(exportProduct.getId());
            exportProductVo.setExportId(id);

            epVoList.add(exportProductVo);
        }
        //合并海关两个数据
        exportVo.setProducts(epVoList);
        //第一次调用海关接口,将实体类传给海关,返回成功与否
        WebClient wc= WebClient.create("http://localhost:8088/ws/export/user");
        wc.post(exportVo);
        //第二次调用海关接口,查询海关是否通过报运,通过则返回一个实体类,包含所需的税金
        wc = WebClient.create("http://localhost:8088/ws/export/user/"+id);
        ExportResult exportResult = wc.get(ExportResult.class);
        System.out.println("========================调用海关接口："+exportResult.toString());
        //将税金更新到export表中
        exportService.updateE(exportResult);
        return "redirect:/cargo/export/list.do";
    }

    @RequestMapping("/exportPdf")
    public void exportPdf(String id) throws Exception {
        //1.map
        Export export = exportService.findById(id);

        Map<String,Object> map = BeanMapUtils.beanToMap(export);
        //list
        ExportProductExample exportProductExample = new ExportProductExample();
        ExportProductExample.Criteria criteria = exportProductExample.createCriteria();
        criteria.andExportIdEqualTo(id);
        List<ExportProduct> list = exportProductService.findAll(exportProductExample);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(list);
        //path
        String path = session.getServletContext().getRealPath("/")+"/jasper/export.jasper";

        JasperPrint jasperPrint = JasperFillManager.fillReport(path,map,dataSource);

        JasperExportManager.exportReportToPdfStream(jasperPrint,response.getOutputStream());
    }
}
