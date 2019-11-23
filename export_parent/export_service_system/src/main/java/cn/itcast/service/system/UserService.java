package cn.itcast.service.system;

import cn.itcast.domain.system.User;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {

    PageInfo findAll(String companyId,Integer page,Integer size);

    List<User> findAll(String companyId);

    void save(User user);

    User findById(String id);

    void update(User user);

    void delete(String id);

    User findByEmail(String email);
}
