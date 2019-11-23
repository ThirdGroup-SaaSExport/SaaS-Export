package cn.itcast.service.system;

import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ModuleService {
    PageInfo findAll(Integer page,Integer size);

    List<Module> findAll();

    int save(Module module);

    int update(Module module);

    Module findById(String id);

    int delete(String id);

    List<Module> findByRoleId(String roleId);

    void insertRoleModule(String roleid, String moduleIds);

    List<Module> findModuleByUser(User user);
}
