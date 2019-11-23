package cn.itcast.web.controller.cargo;

import cn.itcast.domain.cargo.*;
import cn.itcast.service.cargo.ExtCProductService;
import cn.itcast.service.cargo.FactoryService;
import cn.itcast.service.cargo.ProductMessageService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/cargo/extCproduct")
public class ExtCproductController extends BaseController {

    @Reference
    private FactoryService factoryService;

    @Reference
    private ExtCProductService extCProductService;
    @Reference
    private ProductMessageService productMessageService;

    @RequestMapping(value = "/list")
    public String list(String contractId, String contractProductId, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){

        //1、通过factoryService查询所有的厂家，条件必须附件
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryCriteria = factoryExample.createCriteria();
        factoryCriteria.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        //2、factoryList放入request域当中
        request.setAttribute("factoryList", factoryList);

        //查询所有货号并且封装到前台页面
        List<ProductMessage> productMessageList = productMessageService.findAll();
        request.setAttribute("productList",productMessageList);

        ExtCproductExample extCproductExample = new ExtCproductExample();
        ExtCproductExample.Criteria criteria = extCproductExample.createCriteria();
        criteria.andContractProductIdEqualTo(contractProductId);
        PageInfo pageInfo = extCProductService.findAll(extCproductExample, page, size);
        request.setAttribute("page", pageInfo);
        request.setAttribute("contractId", contractId);
        request.setAttribute("contractProductId", contractProductId);
        return "cargo/extc/extc-list";
    }

    //根据货号查询货物的详情封装到前台
    @RequestMapping("/findById")
    public @ResponseBody
    String findById(String productNo){
        ProductMessage productMessage = productMessageService.findByProductId(productNo);
        String productDesc = productMessage.getProductDesc();
        return productDesc;
    }

    @RequestMapping("/edit")
    public String edit(ExtCproduct extCproduct,String contractId, String contractProductId){
        extCproduct.setCompanyId(companyId);
        extCproduct.setCompanyName(companyName);
        if (StringUtil.isEmpty(extCproduct.getId())){
            extCproduct.setContractId(contractId);
            extCproduct.setContractProductId(contractProductId);
            extCProductService.save(extCproduct);
        }else {
            extCProductService.update(extCproduct);
        }

        return "redirect:/cargo/extCproduct/list.do?contractId="+contractId+"&contractProductId="+contractProductId;
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id,String contractId, String contractProductId){
        //1、通过factoryService查询所有的厂家，条件必须附件
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryCriteria = factoryExample.createCriteria();
        factoryCriteria.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        //2、factoryList放入request域当中
        request.setAttribute("factoryList", factoryList);

        ExtCproduct extCproduct = extCProductService.findById(id);
        request.setAttribute("extCproduct",extCproduct);
        request.setAttribute("contractId",contractId);
        request.setAttribute("contractProductId",contractProductId);
        return "cargo/extc/extc-update";
    }

    @RequestMapping("/delete")
    public String delete(String id,String contractId, String contractProductId){
        extCProductService.delete(id);
        request.setAttribute("contractId",contractId);
        request.setAttribute("contractProductId",contractProductId);
        return "redirect:/cargo/extCproduct/list.do?contractId="+contractId+"&contractProductId="+contractProductId;
    }
}
