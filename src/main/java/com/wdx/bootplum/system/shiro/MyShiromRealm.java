package com.wdx.bootplum.system.shiro;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wdx.bootplum.system.consts.ShiroConst;
import com.wdx.bootplum.system.entity.SysUserDO;
import com.wdx.bootplum.system.entity.SysUserDTO;
import com.wdx.bootplum.system.service.ISysMenuService;
import com.wdx.bootplum.system.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @Author wangwei
 * @Description //TODO 自定义Realm，实现认证授权
 * @Date 15:06 2019-03-11
 * @Param
 * @return
 **/
public class MyShiromRealm extends AuthorizingRealm {
    @Autowired
    ISysUserService sysUserService;
    @Autowired
    ISysMenuService sysMenuService;

    /**
     * @return org.apache.shiro.authz.AuthorizationInfo
     * @Author wangwei
     * @Description //TODO 重写父类AuthorizingRealm的方法，用于授权
     * @Date 14:31 2019-03-11
     * @Param [principalCollection]
     **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Session session = SecurityUtils.getSubject().getSession();
        SysUserDO sysUserDO = (SysUserDO) session.getAttribute("USER_SESSION");
        Set<String> perms = sysUserDO.getPerms();
        authorizationInfo.setStringPermissions(perms);
        return authorizationInfo;
    }

    /**
     * @return org.apache.shiro.authc.AuthenticationInfo
     * @Author wangwei
     * @Description //TODO 重写父类AuthenticatingRealm的方法，用于认证
     * @Date 14:31 2019-03-11
     * @Param [authenticationToken]
     **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String passWord = new String((char[]) token.getCredentials());
        SysUserDO user = sysUserService.getOne(new QueryWrapper<SysUserDO>().eq("username", username).eq("password", passWord));
        // 账号不存在
        if (user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        // 账号锁定
        if (user.getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        //查出权限信息
        Set<String> perms = sysMenuService.listPerms(user.getUserId());
        user.setPerms(perms);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, passWord, getName());
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute(ShiroConst.SESSION_USER_NAME, user);
        return info;
    }
}
