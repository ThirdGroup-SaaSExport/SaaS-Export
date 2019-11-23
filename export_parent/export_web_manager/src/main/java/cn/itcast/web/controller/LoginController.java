package cn.itcast.web.controller;


import cn.itcast.common.utils.MailUtil;
import cn.itcast.domain.Msg;
import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.ModuleService;
import cn.itcast.service.system.UserService;
import com.github.pagehelper.util.StringUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class LoginController extends BaseController{

    @Autowired
    private UserService userService;
    @Autowired
    private ModuleService moduleService;
	@RequestMapping("/login")
	public String login(String email,String password) {
        /*//判断用户输入的email和password是否为空
        if (StringUtil.isEmpty(email)||StringUtil.isEmpty(password)){
            request.setAttribute("error","请输入用户名或密码");
            return "forward:login.jsp";
        }

        //3、通过email查询用户实体类
        User user=userService.findByEmail(email);

        //判断输入密码是否正确
        if (user!=null&&user.getPassword().equals(password)){
            //查找用户对应的菜单（模块）
            List<Module> moduleList = moduleService.findModuleByUser(user);
            session.setAttribute("modules",moduleList);
            session.setAttribute("loginUser",user);
            return "home/main";
        }else {
            request.setAttribute("error","用户名或密码错误");
            return "forward:login.jsp";
        }*/

        try {
            //创建subject
            Subject subject = SecurityUtils.getSubject();
            //创建token
            UsernamePasswordToken upToken= new UsernamePasswordToken(email,password);
            //调用subject的login方法
            subject.login(upToken);

            //通过subject取到安全数据user,user在login方法中创建,所以安全
            User user=(User) subject.getPrincipal();

            if (user!=null){
                //将user放入session
                session.setAttribute("loginUser",user);
                List<Module> moduleList = moduleService.findModuleByUser(user);
                session.setAttribute("modules", moduleList);

                //判断是否为企业管理员
                if (user.getDegree()==0){
                    List<Msg> msgs = MailUtil.getMsg();
                    //将邮件信息传给前台查看是否有新邮件
                    request.setAttribute("msgs",msgs);
                }
                //5、进行登录
                return "home/main";
            }else {
                request.setAttribute("error", "用户不存在！");
                return "forward:login.jsp";
            }
        } catch (Exception e) {
            request.setAttribute("error", "用户名或密码不正确！");
            return "forward:login.jsp";
        }
    }

    //查看邮件
    @RequestMapping("/getemail")
    public String getFeedback() throws Exception {
        List<Msg> msgs = MailUtil.getMsg();
        request.setAttribute("msgs",msgs);
        return "getemail";
    }

    //退出
    @RequestMapping(value = "/logout",name="用户登出")
    public String logout(){
        SecurityUtils.getSubject().logout();   //登出
        return "forward:login.jsp";
    }

    @RequestMapping("/toupdate")
    public String toUpdate(){
	    return "home/update";
    }

    @RequestMapping("/save")
    public String save(String password){
	    loginUser.setPassword(password);
	    userService.update(loginUser);
	    return "home/main";
    }

    @RequestMapping("/home")
    public String home(){
	    return "home/home";
    }
}
