package com.ween.asset.config;

import com.ween.asset.entity.Person;
import com.ween.asset.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * Created by wen on 2018/8/21
 */
public class UserShiroRealm extends AuthorizingRealm{

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        Person person= (Person) principals.getPrimaryPrincipal();
        //todo  配置用户权限
        return authorizationInfo;
    }

    //身份认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken token1=(UsernamePasswordToken) token;
        String userName=token1.getUsername();
        Person person=userService.findPersonByUserCode(userName);
        if(person==null){
            throw new  UnknownAccountException();
        }
        return new SimpleAuthenticationInfo(person,person.getPassWord(),getName());
    }
}
