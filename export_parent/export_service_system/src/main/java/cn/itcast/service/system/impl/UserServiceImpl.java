package cn.itcast.service.system.impl;

import cn.itcast.common.utils.Encrypt;
import cn.itcast.dao.system.UserDao;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    public PageInfo findAll(String companyId,Integer page,Integer size) {
        PageHelper.startPage(page,size);
        List<User> list=userDao.findAll(companyId);
        return new PageInfo(list);
    }

    public List<User> findAll(String companyId) {
        return userDao.findAll(companyId);
    }

    //保存用户信息
    public void save(User user) {
        user.setId(UUID.randomUUID().toString());
        if (StringUtil.isEmpty(user.getDeptId())){
            user.setDeptId(null);
        }
        user.setPassword(Encrypt.md5(user.getPassword(), user.getEmail()));
        userDao.save(user);
    }

    public User findById(String id) {
        return userDao.findById(id);
    }

    public void update(User user) {
        user.setPassword(Encrypt.md5(user.getPassword(), user.getEmail()));
        userDao.update(user);
    }

    public void delete(String id) {
        userDao.delete(id);
    }

    //通过邮箱查找User
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
