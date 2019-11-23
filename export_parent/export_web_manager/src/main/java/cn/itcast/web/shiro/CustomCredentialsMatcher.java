package cn.itcast.web.shiro;

import cn.itcast.common.utils.Encrypt;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
//密码比较
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //两个参数,对比输入的数据和数据库的数据
        //强转token
        UsernamePasswordToken upToken=(UsernamePasswordToken)token;
        //获取输入的密码
        String userPassword= String.valueOf(upToken.getPassword());
        //获取加密所需要的盐(用户名)
        String email=upToken.getUsername();
        //加密
        String md5Password= Encrypt.md5(userPassword,email);

        //获取数据库密码
        String dbPassword= String.valueOf(info.getCredentials());
        //返回比较结果
        return md5Password.equals(dbPassword);
    }
}
