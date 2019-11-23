package cn.itcast.web.controller.system;

import cn.itcast.domain.system.Dept;
import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.Role;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.DeptService;
import cn.itcast.service.system.ModuleService;
import cn.itcast.service.system.RoleService;
import cn.itcast.service.system.UserService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/list",name = "查询所有")
    public String findAll(@RequestParam(defaultValue = "1") Integer page,@RequestParam (defaultValue = "5") Integer size){
        PageInfo pageInfo = roleService.findAll(companyId, page, size);
        request.setAttribute("page",pageInfo);
        return "system/role/role-list";
    }

    @RequestMapping(value = "/toAdd",name = "跳转到新建页面")
    public String toAdd(){
        return "system/role/role-add";
    }

    @RequestMapping(value = "/edit",name = "编辑角色")
    public String edit(Role role){
        role.setCompanyId(companyId);
        role.setCompanyName(companyName);
        if (StringUtil.isEmpty(role.getId())){
            roleService.save(role);
        }else {
            roleService.update(role);
        }
        return "redirect:/system/role/list.do";
    }

    @RequestMapping(value = "/toUpdate",name = "跳转编辑角色页面")
    public String toUpdate(String id){
        Role role = roleService.findById(id);
        request.setAttribute("role",role);
        return "system/role/role-update";
    }

    @RequestMapping(value = "/delete",name = "删除角色信息")
    public String delete(String id){
        roleService.delete(id);
        return "redirect:/system/role/list.do";
    }

    @RequestMapping(value = "/roleModule",name = "跳转到权限管理页面")
    public String roleModule(String roleid){
        Role role= roleService.findById(roleid);
        request.setAttribute("role",role);
        return "system/role/role-module";
    }

    @Autowired
    private ModuleService moduleService;

    @RequestMapping(value = "/initModuleData",name = "从后台查找模块信息")
    public @ResponseBody List<Map> initModuleData(String roleId){//将List转换成Json格式
        List<Module> moduleList = moduleService.findAll();

        List<Map> zNodes=new ArrayList<Map>();

        List<Module> roleModule= moduleService.findByRoleId(roleId);
        for (Module module:moduleList){
            Map zNode=new HashMap();
            zNode.put("id",module.getId());
            zNode.put("pId",module.getParentId());
            zNode.put("name",module.getName());
            if (roleModule.contains(module)){
                zNode.put("checked", true);
            }
            zNodes.add(zNode);
        }
        System.out.println(zNodes);
        return zNodes;
    }

    @RequestMapping(value = "/updateRoleModule",name = "保存角色的模块信息,先删除后保存")
    public String updateRoleModule(String roleid,String moduleIds){
        moduleService.insertRoleModule(roleid,moduleIds);
        return "redirect:/system/role/list.do";
    }
}

