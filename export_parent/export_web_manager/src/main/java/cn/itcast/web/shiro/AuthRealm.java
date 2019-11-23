package cn.itcast.web.shiro;

import cn.itcast.domain.system.Module;
import cn.itcast.domain.system.User;
import cn.itcast.service.system.ModuleService;
import cn.itcast.service.system.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AuthRealm extends AuthorizingRealm {

    @Autowired
    private ModuleService moduleService;
    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //通过user获取一个set设置到SimpleAuthorizationInfo中
        //获取安全数据User
        User user=(User)principalCollection.getPrimaryPrincipal();
        //获取菜单信息
        List<Module> moduleList = moduleService.findModuleByUser(user);
        Set<String> set = new HashSet<>();
        for (Module module : moduleList) {
            set.add(module.getName());
        }


        //设置StringPermissions值
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.setStringPermissions(set);
        return info;
    }

    @Autowired
    private UserService userService;
    //身份认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.获取AuthenticationInfo的第一个参数User
        //将token强转
        UsernamePasswordToken upToken= (UsernamePasswordToken) authenticationToken;
        //获取用户名
        String email = upToken.getUsername();
        //得到user
        User user=  userService.findByEmail(email);

        //Object principal 安全数据user实体类
        // Object credentials 用户的密码
        // String realmName 可以随意取名类名
        AuthenticationInfo info=new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
        return info;
    }
}
