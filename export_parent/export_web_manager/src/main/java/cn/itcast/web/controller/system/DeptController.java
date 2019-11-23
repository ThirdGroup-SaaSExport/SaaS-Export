package cn.itcast.web.controller.system;

import cn.itcast.domain.system.Dept;
import cn.itcast.service.system.DeptService;
import cn.itcast.web.controller.BaseController;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/system/dept")
public class DeptController extends BaseController {

    @Autowired
    private DeptService deptService;

    @RequestMapping("/list")
    public String findAll(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "5") Integer size){
        PageInfo pageInfo = deptService.findAll(companyId, page, size);
        request.setAttribute("page",pageInfo);
        return "system/dept/dept-list";
    }

    @RequestMapping("/toAdd")
    public String toAdd(){
        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("deptList",deptList);
        return "system/dept/dept-add";
    }
    @RequestMapping(value = "/edit",name = "保存部门信息")
    public String save(Dept dept){
        dept.setCompanyId(companyId);
        dept.setCompanyName(companyName);
        if (StringUtil.isEmpty(dept.getId())){
            deptService.save(dept);
        }else {
            deptService.update(dept);
        }
        return "redirect: /system/dept/list.do";
    }

    @RequestMapping(value = "/toUpdate",name = "修改部门信息")
    public String toUpdate(String id){
        Dept dept= deptService.findById(id);
        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("deptList",deptList);
        request.setAttribute("dept",dept);
        return "system/dept/dept-update";
    }

    @RequestMapping(value = "/delete",name = "删除部门信息")
    public String delete(String id){
        deptService.delete(id);
        return "redirect:/system/dept/list.do";
    }
}
