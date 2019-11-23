package cn.itcast.service.system.impl;

import cn.itcast.dao.system.ModuleDao;
import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.ModuleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleDao moduleDao;

    public PageInfo findAll(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<Module> list=moduleDao.findAll();
        return new PageInfo(list);
    }

    public List<Module> findAll() {
        return moduleDao.findAll();
    }

    public int save(Module module) {
        module.setId(UUID.randomUUID().toString());
        return moduleDao.save(module);
    }

    public int update(Module module) {
       return moduleDao.update(module);
    }

    //通过id查找模块
    public Module findById(String id) {

        return moduleDao.findById(id);
    }

    public int delete(String id) {
        return moduleDao.delete(id);
    }

    public List<Module> findByRoleId(String roleId) {
        return moduleDao.findByRoleId(roleId);
    }

    public void insertRoleModule(String roleid,String moduleIds) {
        //先通过roleid删除中间表中moduleID
        moduleDao.deleteByRoleId(roleid);

        //将moduleIds转换成数组
        String[] modules=moduleIds.split(",");

        //添加模块信息
        for (String moduleId:modules){
            moduleDao.insertRoleModule(roleid,moduleId);
        }
    }
//判断用户是什么SaaS管理员企业管理员或其他,显示其对应的菜单模块
    /**
     * pe_user  degree
     * 0-saas管理员
     * 1-企业管理员
     * 其他-企业用户
     *
     *
     * ss_module  belong
     * 0：sass系统内部菜单
     * 1：租用企业菜单
     * @param user
     * @return
     */
    public List<Module> findModuleByUser(User user) {
        if (user.getDegree()==0){
            //1、SaaS平台管理员   如果用户是saas管理员，对应的模块 belong=0
            return moduleDao.findByDegree(user.getDegree());
        }else if (user.getDegree()==1){
            //2、企业管理员       如果用户是企业管理员，对应的模块 belong=1
            return moduleDao.findByDegree(user.getDegree());
        }else {
            //3、企业其他人员     如果用户是企业其他人员，对应的模块查询  用户角色中间表，角色模块中间表 --> 用户对应的模块
            return moduleDao.findModuleByUserId(user.getId());
        }
    }
}
