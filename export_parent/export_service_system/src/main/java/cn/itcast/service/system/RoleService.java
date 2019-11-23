package cn.itcast.service.system;

import cn.itcast.domain.system.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {
    //查询所有
    PageInfo findAll(String companyId,Integer page,Integer size);
    //根据id进行查询
    Role findById(String id);
    //保存
    int save (Role role);
    //更新
    public int update(Role role);
    //删除
    public int delete(String id);

    List<Role> findUserRoleByUserId(String id);

    void changeRole(String userId, String[] roleIds);

    List<Role> findAll(String companyId);
}
