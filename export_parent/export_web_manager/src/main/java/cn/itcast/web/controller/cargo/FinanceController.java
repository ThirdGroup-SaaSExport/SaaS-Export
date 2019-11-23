package cn.itcast.web.controller.cargo;
//
//import cn.itcast.domain.cargo.Invoice;
//import cn.itcast.domain.cargo.InvoiceExample;
//import cn.itcast.service.cargo.InvoiceService;
import cn.itcast.web.controller.BaseController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/cargo/finance")
public class FinanceController extends BaseController {

//    @Reference
//    private InvoiceService invoiceService;
//    @RequestMapping("/list")
//    public String list(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "5") int size){
//        InvoiceExample invoiceExample = new InvoiceExample();
//        InvoiceExample.Criteria criteria = invoiceExample.createCriteria();
//        criteria.andStateEqualTo(1);
//        PageInfo pageInfo = invoiceService.findAll(invoiceExample,page,size);
//        request.setAttribute("page", pageInfo);
//        return "/cargo/finance/finance-list";
//    }

}
