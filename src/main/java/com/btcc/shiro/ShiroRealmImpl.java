package com.btcc.shiro;

import com.btcc.web.login.entity.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created by peiyou on 10/24/16.
 */
public class ShiroRealmImpl extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {

        // 得到 doGetAuthenticationInfo 方法中传入的凭证
        User shiroUser = (User) super.getAvailablePrincipal(principals);

        /*String userName = shiroUser.getUsername();
        if (StringUtils.equals("admin", userName)) {

            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

            // 这个确定页面中<shiro:hasRole>标签的name的值
            info.addRole("admin");
            // 这个就是页面中 <shiro:hasPermission> 标签的name的值
            info.addStringPermission("user:edit");

            return info;
        } else if (StringUtils.equals("test", userName)) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

            // 这个确定页面中<shiro:hasRole>标签的name的值
            info.addRole("test");
            // 这个就是页面中 <shiro:hasPermission> 标签的name的值, 这个串 随便的,不过我还是认为 白衣的好。
            info.addStringPermission("user:view");

            return info;
        } else {
            return null;
        }*/
        return new SimpleAuthorizationInfo();
    }


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken usernamePasswordToke = (UsernamePasswordToken) token;

        String username = usernamePasswordToke.getUsername();
        String encodedPassword = new Sha256Hash(
                usernamePasswordToke.getPassword()).toBase64();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(new User(null,username,String.valueOf(usernamePasswordToke.getPassword())) , String.valueOf(usernamePasswordToke.getPassword()), getName());

        return  info;
    }

}
