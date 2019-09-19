package com.wdx.bootplum.common.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wdx.bootplum.common.constant.PageCons;
import com.wdx.bootplum.common.utils.AntiSQLFilter;
import com.wdx.bootplum.common.utils.TypeUtils;
import com.wdx.bootplum.system.entity.SysUserDO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Author wangwei
 * @Description //TODO 基础controller
 * @Date 14:20 2019-03-21
 * @Param
 * @return
 **/
public class BaseController {

    @Autowired
    protected HttpServletRequest request;


    public Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    public SysUserDO getUser() {
        Object object = SecurityUtils.getSubject().getPrincipal();
        return (SysUserDO) object;
    }

    public String getUserId() {
        return getUser().getUserId();
    }

    public String getUsername() {
        return getUser().getUsername();
    }


    /**
     * 获取分页对象
     *
     * @return
     */
    protected <T> Page<T> getPage() {
        return getPage(false);
    }

    /**
     * 获取分页对象
     *
     * @param openSort
     * @return
     */
    protected <T> Page<T> getPage(boolean openSort) {
        int index = 1;
        // 页数 默认1
        Integer cursor = TypeUtils.castToInt(request.getParameter(PageCons.PAGE_PAGE), index);
        // 分页大小 默认20
        Integer limit = TypeUtils.castToInt(request.getParameter(PageCons.PAGE_ROWS), PageCons.DEFAULT_LIMIT);
        limit = limit > PageCons.MAX_LIMIT ? PageCons.MAX_LIMIT : limit;

        Page<T> page = new Page(cursor, limit);
        if (openSort) {
            page.setAscs(Arrays.asList(getParameterSafeValues(PageCons.PAGE_ASCS)));
            page.setDescs(Arrays.asList(getParameterSafeValues(PageCons.PAGE_DESCS)));
        }
        return page;
    }

    /**
     * 获取安全参数(SQL ORDER BY 过滤)
     *
     * @param parameter
     * @return
     */
    protected String[] getParameterSafeValues(String parameter) {
        return AntiSQLFilter.getSafeValues(request.getParameterValues(parameter));
    }
}
