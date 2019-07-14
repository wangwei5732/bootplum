package com.wdx.bootplum.system.shiro;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author wangwei
 * @Description //TODO 自定义shirofilter：用来解决cors请求option时会产生跨域问题
 * @Date 15:46 2019-06-10
 * @Param
 * @return
 **/
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {
    //OPTIONS常量字符串
    private String OPTIONSTR = "OPTIONS";
    /**
     * @Author wangwei
     * @Description //TODO 处理shiro的跨域问题：拦截OPTIONS
     * @Date 15:45 2019-06-10
     * @Param [request, response, mappedValue]
     * @return boolean
     **/
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (request instanceof HttpServletRequest) {
            if (((HttpServletRequest) request).getMethod().toUpperCase().equals(OPTIONSTR)) {
                return true;
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }
}
