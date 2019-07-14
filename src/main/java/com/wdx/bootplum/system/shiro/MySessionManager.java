package com.wdx.bootplum.system.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * @Author wangwei
 * @Description //TODO 自定义sessionManager，使用Redis实现Session管理
 * @Date 15:34 2019-03-11
 * @Param
 * @return
 **/
public class MySessionManager extends DefaultWebSessionManager {

    public static String TOKEN_NAME = "token";
    public static String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String token = WebUtils.toHttp(request).getHeader(TOKEN_NAME);
        if (StringUtils.isNotEmpty(token)) {
            // #优化 根据https://www.w3xue.com/exp/article/201811/7449.html拷贝过来的，后期再优化
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, token);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return token;
        } else {
            return super.getSessionId(request, response);
        }
    }
}
