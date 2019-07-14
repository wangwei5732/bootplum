package com.wdx.bootplum.system.config;

import com.wdx.bootplum.system.shiro.MyFormAuthenticationFilter;
import com.wdx.bootplum.system.shiro.MySessionManager;
import com.wdx.bootplum.system.shiro.MyShiromRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther: wangwei
 * @Date: 2019-03-09 15:04
 * @Description:
 */
@Configuration
public class ShiroConfig {

    /**
     * @return org.apache.shiro.spring.web.ShiroFilterFactoryBean
     * @Author wangwei
     * @Description //TODO ShiroFilterFactoryBean 处理拦截器问题。
     * 注意：初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     * Web应用中,Shiro可控制的Web请求必须经过Shiro主过滤器的拦截
     * @Date 15:08 2019-03-09
     * @Param [securityManager]
     **/
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {

        // 1.创建 ShiroFilterFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.getFilters().put("authc", new MyFormAuthenticationFilter());
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 2.配置 登录、未授权、登录成功路径
        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/unauth");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 3.配置过滤器：过滤器注意顺序，优先匹配前面的
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了，登出后跳转配置的loginUrl
        filterChainDefinitionMap.put("/logout", "logout");
        //配置不需要拦截的路径
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/ajaxLogin", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/SysTask/sysTaskDO/**", "anon");

        //websocket放行路径
        filterChainDefinitionMap.put("/ws/**", "anon");
        //Swagger放行
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/csrf", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");

        //配置测试放行路径
        filterChainDefinitionMap.put("/api/**", "anon");

        filterChainDefinitionMap.put("/**", "authc");
//        filterChainDefinitionMap.put("/**", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * @return org.apache.shiro.mgt.SecurityManager
     * @Author wangwei
     * @Description //TODO  配置核心安全事务管理器:securityManager
     * @Date 15:29 2019-03-09
     * @Param []
     **/
    @Bean(name = "securityManager")
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //1.设置自定义realm.
        securityManager.setRealm(myShiromRealm());
        //2.设置自定义sessionManager
        securityManager.setSessionManager(sessionManager());
        //3.设置自定义cacheManager
        securityManager.setCacheManager(redisCacheManager());
        return securityManager;
    }

    /**
     * @return com.wdx.bootplum.system.shiro.MyShiromRealm
     * @Author wangwei
     * @Description //TODO 1.自定义ShiroRealm，登录、授权自己实现
     * @Date 15:'13 2019-03-11
     * @Param []
     **/
    @Bean(name = "myShiroRealm")
    public MyShiromRealm myShiromRealm() {
        MyShiromRealm shiromRealm = new MyShiromRealm();
        return shiromRealm;
    }

    /**
     * @return org.apache.shiro.session.mgt.SessionManager
     * @Author wangwei
     * @Description //TODO 2.自定义sessionManager
     * @Date 16:24 2019-03-11
     * @Param []
     **/
    @Bean(name = "sessionManager")
    public SessionManager sessionManager() {
        MySessionManager mySessionManager = new MySessionManager();
        mySessionManager.setSessionDAO(redisSessionDAO());
        return mySessionManager;
    }

    /**
     * @return org.crazycake.shiro.RedisCacheManager
     * @Author wangwei
     * @Description //TODO 3.自定义redisCacheManager
     * @Date 17:08 2019-03-11
     * @Param []
     **/
    @Bean(name = "redisCacheManager")
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }

    /**
     * @return org.crazycake.shiro.RedisSessionDAO
     * @Author wangwei
     * @Description //TODO 2.1shiro sessionDao层的实现 通过redis
     * @Date 16:24 2019-03-11
     * @Param []
     **/
    @Bean(name = "redisSessionDAO")
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * @return org.crazycake.shiro.RedisManager
     * @Author wangwei
     * @Description //TODO redisManager 使用的是shiro-redis开源插件
     * @Date 16:25 2019-03-11
     * @Param []
     **/
    @Bean(name = "redisManager")
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("39.105.92.184:6379");
        redisManager.setPassword("123456");
        redisManager.setTimeout(1800);
        return redisManager;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
