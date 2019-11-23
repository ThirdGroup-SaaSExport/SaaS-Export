package cn.itcast.web.controller.company;

import cn.itcast.common.entity.PageResult;
import cn.itcast.domain.company.Company;
import cn.itcast.service.company.CompanyService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {
    @Autowired
    private CompanyService companyService;

    //查询所有
    @RequestMapping("/list")
    public String findAll(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size) {
//        PageResult pageResult = companyService.findAll(page, size);
//        request.setAttribute("page", pageResult);
        PageInfo pageInfo=companyService.findByHelper(page,size);
        request.setAttribute("page",pageInfo);
        return "company/company-list";
    }

    //跳转到添加信息页面
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "company/company-add";
    }

    @RequestMapping("/edit")
    public String edit(Company company) {
        if (StringUtil.isEmpty(company.getId())) {
            companyService.save(company);
        } else {
            companyService.update(company);
        }
        return "redirect: /company/list.do";
    }

    @RequestMapping("/toUpdate")
    public String update(String id) {
        Company company = companyService.findById(id);
        request.setAttribute("company", company);
        return "company/company-update";
    }

    @RequestMapping("/delete")
    public String delete(String id) {
        companyService.delete(id);
        return "redirect:/company/list.do";
    }
}
