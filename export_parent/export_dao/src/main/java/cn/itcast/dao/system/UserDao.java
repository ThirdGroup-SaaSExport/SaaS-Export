package cn.itcast.dao.system;

import cn.itcast.domain.system.User;

import java.util.List;

public interface UserDao {

    List<User> findAll(String companyId);

    //保存用户信息
    void save(User user);

    User findById(String id);

    //修改用户信息
    void update(User user);

    void delete(String id);

    //通过邮箱查找User
    User findByEmail(String email);
}
