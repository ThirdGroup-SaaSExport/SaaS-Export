package cn.itcast.dao.system;

import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleDao {
    //查询所有
    List<Module> findAll();

    int save(Module module);

    Module findById(String id);

    int update(Module module);

    int delete(String id);

    List<Module> findByRoleId(String roleId);

    void deleteByRoleId(String roleid);

    void insertRoleModule(@Param("roleid") String roleid,@Param("moduleId")  String moduleId);

    List<Module> findByDegree(Integer belong);

    List<Module> findModuleByUserId(String id);
}
