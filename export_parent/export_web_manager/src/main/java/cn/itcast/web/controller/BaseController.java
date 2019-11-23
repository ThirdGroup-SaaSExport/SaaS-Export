package cn.itcast.web.controller;

import cn.itcast.domain.system.User;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {
    protected HttpServletRequest request;
    protected HttpSession session;
    protected HttpServletResponse response;
    protected String companyId;
    protected String companyName;
    protected User loginUser;

    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        this.request = request;
        this.response = response;
        this.session = session;

//        //还没有用户数据 所以模拟一下加个测试数据
//        this.companyId="1";
//        this.companyName="传智播客教育股份有限公司";
//

        User user = (User) session.getAttribute("loginUser");
        if (user != null) {
            this.loginUser = user;
            this.companyId = user.getCompanyId();
            this.companyName = user.getCompanyName();
        }
    }


}
