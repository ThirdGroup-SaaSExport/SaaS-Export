package cn.itcast.web.controller.cargo;

import cn.itcast.common.utils.UploadUtil;
import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ContractProductService;
import cn.itcast.service.cargo.FactoryService;
import cn.itcast.service.cargo.ProductMsgService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/cargo/contractProduct")
public class ContractProductController extends BaseController {

    @Reference
    private FactoryService factoryService;

    @Reference
    private ContractProductService contractProductService;

    @Reference
    private ProductMsgService productMsgService;

    @RequestMapping("/list")
    public String list(String contractId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size) {
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria1 = factoryExample.createCriteria();
        criteria1.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList", factoryList);

        //查询所有货号并且封装到前台页面
        List<ProductMsg> productMsgList = productMsgService.findAll();
        System.out.println(productMsgList);
        request.setAttribute("productList",productMsgList);


        ContractProductExample example = new ContractProductExample();
        ContractProductExample.Criteria criteria = example.createCriteria();
        criteria.andContractIdEqualTo(contractId);
        PageInfo pageInfo = contractProductService.findAll(example, page, size);
        request.setAttribute("page", pageInfo);
        request.setAttribute("contractId", contractId);
        return "cargo/product/product-list";
    }
    //根据货号查询货物的详情封装到前台
    @RequestMapping("/findById")
    public @ResponseBody
    String findById(String productNo){
        ProductMsg productMsg = productMsgService.findByProductId(productNo);
        String loadingRate = productMsg.getLoadingRate();
        System.out.println(loadingRate);
        return loadingRate;
    }

    @RequestMapping("/edit")
    public String edit(String contractId, ContractProduct contractProduct, MultipartFile productPhoto) throws IOException {
        contractProduct.setCompanyId(companyId);
        contractProduct.setCompanyName(companyName);

        if (!productPhoto.isEmpty()) {
            String imgUrl = new UploadUtil().upload(productPhoto.getBytes());
            contractProduct.setProductImage(imgUrl);
        }
        if (StringUtil.isEmpty(contractProduct.getId())) {
            contractProductService.save(contractProduct);
        } else {
            contractProductService.update(contractProduct);
        }
        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractId;
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria criteria1 = factoryExample.createCriteria();
        criteria1.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList", factoryList);
        //回显数据
        ContractProduct contractProduct = contractProductService.findById(id);
        request.setAttribute("contractProduct", contractProduct);
        return "cargo/product/product-update";
    }

    @RequestMapping("/delete")
    public String delete(String id, String contractId) {
        contractProductService.delete(id);
        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractId;
    }

    @RequestMapping("/toImport")
    public String toImport(String contractId) {
        request.setAttribute("contractId", contractId);
        return "cargo/product/product-import";
    }

    @RequestMapping("/import")
    public String importProduct(String contractId, MultipartFile file) throws Exception {
        //创建一个工作簿
        Workbook wb = new XSSFWorkbook(file.getInputStream());
        //获取第一页
        Sheet sheet = wb.getSheetAt(0);
        Object[] strings = new Object[10];
        //循环行
        for (int i = 1; i <3; i++) {
            //获取第一行
            Row row = sheet.getRow(i);
            //循环获取单元格获取
            for (int j = 1; j < 10; j++) {
                Cell cell = row.getCell(j);
                strings[j] = getCellValue(cell);
            }
            ContractProduct contractProduct = new ContractProduct(strings, companyId, companyName);
            contractProduct.setContractId(contractId);
            contractProductService.save(contractProduct);
        }
        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractId;
    }

    public Object getCellValue(Cell cell) {
        Object object = new Object();

        switch (cell.getCellType()) {
            case STRING:
                object = cell.getStringCellValue();
                break;
            case BOOLEAN:
                object = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    object = new SimpleDateFormat("yyyy-MM-dd").format(cell.getDateCellValue());
                } else {
                    object = cell.getNumericCellValue();
                }
                break;
            case FORMULA:
                break;
        }

        return object;
    }
}
