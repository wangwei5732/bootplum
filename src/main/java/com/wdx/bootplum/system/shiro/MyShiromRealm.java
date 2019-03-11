package com.wdx.bootplum.system.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @Author wangwei
 * @Description //TODO 自定义Realm，实现认证授权
 * @Date 15:06 2019-03-11
 * @Param 
 * @return 
 **/
public class MyShiromRealm extends AuthorizingRealm {
    /**
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @Author wangwei
     * @Description //TODO 重写父类AuthorizingRealm的方法，用于授权
     * @Date 14:31 2019-03-11
     * @Param [principalCollection]
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @Author wangwei
     * @Description //TODO 重写父类AuthenticatingRealm的方法，用于认证
     * @Date 14:31 2019-03-11
     * @Param [authenticationToken]
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
