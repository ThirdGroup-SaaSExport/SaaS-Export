package cn.itcast.service.system.impl;

import cn.itcast.dao.system.RoleDao;
import cn.itcast.domain.system.Role;
import cn.itcast.service.system.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    //分页查询
    public PageInfo findAll(String companyId,Integer page,Integer size) {
        PageHelper.startPage(page,size);
        List<Role> list=roleDao.findAll(companyId);
        return new PageInfo(list);
    }

    //根据id进行查询
    public Role findById(String id) {
        return roleDao.findById(id);
    }

    //保存
    public int save(Role role) {
        role.setId(UUID.randomUUID().toString());
        return roleDao.save(role);
    }

    //更新
    public int update(Role role) {
        return roleDao.update(role);
    }

    //删除
    public int delete(String id) {
        return roleDao.delete(id);
    }

    //通过userid查询用户的角色信息
    public List<Role> findUserRoleByUserId(String id) {

        return roleDao.findUserRoleByUserId(id);
    }

    //保存用户划分角色信息
    public void changeRole(String userId, String[] roleIds) {
        //先删除中间表用户的角色信息
        roleDao.deleteByUserId(userId);
        //保存中间表的用户新角色信息
        for (String roleId : roleIds) {
            roleDao.insertUserRole(userId,roleId);
        }
    }

    @Override
    public List<Role> findAll(String companyId) {
        return roleDao.findAll(companyId);
    }
}
