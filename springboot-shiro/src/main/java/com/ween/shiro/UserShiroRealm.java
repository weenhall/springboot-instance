package com.ween.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.nio.charset.StandardCharsets;

public class UserShiroRealm extends AuthorizingRealm {
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo authenticationInfo=new SimpleAuthorizationInfo();
		Object person=principals.getPrimaryPrincipal();
		//todo
		authenticationInfo.addRole("ADMIN");
		return authenticationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken token1=(UsernamePasswordToken) token;
		String userName=token1.getUsername();
		return new SimpleAuthenticationInfo("","person",getName());
	}
}
