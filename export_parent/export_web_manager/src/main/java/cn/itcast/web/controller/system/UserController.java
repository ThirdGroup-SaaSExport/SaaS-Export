package cn.itcast.web.controller.system;

import cn.itcast.common.utils.MailUtil;
import cn.itcast.domain.system.Dept;
import cn.itcast.domain.system.Role;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.DeptService;
import cn.itcast.service.system.RoleService;
import cn.itcast.service.system.UserService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private DeptService deptService;

    @RequestMapping(value = "/list", name = "查询所有用户信息")
    public String findAll(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size){
        PageInfo pageInfo=userService.findAll(companyId,page,size);
        request.setAttribute("page",pageInfo);
        return "system/user/user-list";
    }

    @RequestMapping(value = "/toAdd" ,name = "转到保存编写页面")
    public String toAdd(){
        List<Dept> list = deptService.findAll(companyId);
        request.setAttribute("deptList",list);
        return "system/user/user-add";
    }

    @RequestMapping(value = "/edit",name = "保存用户信息")
    public String edit(User user) throws Exception {
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        if (StringUtil.isEmpty(user.getId())){

            String passWord = user.getPassword();
            userService.save(user);
            MailUtil.sendMsg(user.getEmail(),"欢迎来到SaaS项目",
                    "你的用户名是"+user.getEmail()+"你的密码是:"+passWord);
        }else {
            userService.update(user);
        }
        return "redirect:/system/user/list.do";
    }

    @RequestMapping(value = "/toUpdate",name = "跳转到编辑页面")
    public String toUpdate(String id){
        User user=userService.findById(id);
        List<Dept> list = deptService.findAll(companyId);
        request.setAttribute("deptList",list);
        request.setAttribute("user",user);
        return "system/user/user-update";
    }

    @RequestMapping(value = "/delete",name = "删除用户信息")
    public String delete(String id){
        userService.delete(id);
        return "redirect:/system/user/list.do";
    }

    @Autowired
    private RoleService roleService;
    @RequestMapping(value = "/roleList",name = "跳转到用户划分角色页面")
    public String roleList(String id){
        User user = userService.findById(id);
        request.setAttribute("user",user);
        //3、通过roleService查询所有的角色信息，不分页
        List<Role> list=roleService.findAll(companyId);
        request.setAttribute("roleList",list);

        //通过userid查询用户的角色信息
        List<Role> userRoleList=roleService.findUserRoleByUserId(id);

        //循环list构建userRoleStr(用户的角色信息的id数组)
        String userRoleStr="";
        for (Role role : userRoleList) {
            userRoleStr=userRoleStr+role.getId()+",";
        }
        request.setAttribute("userRoleStr",userRoleStr);
        return "system/user/user-role";
    }

    @RequestMapping(value = "/changeRole",name = "保存用户划分角色信息")
    //参数userid要与jsp页面传来的名称要一致 否则接收不到
    public String changeRole(String userid,String[] roleIds){
        //保存roleIds
        System.out.println(userid);
        roleService.changeRole(userid,roleIds);
        return "redirect:/system/user/list.do";
    }


}

