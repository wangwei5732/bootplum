package com.wdx.bootplum.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;


/**
 * @Auther: wangwei
 * @Date: 2019-03-15 16:49
 * @Description:支持跨域
 */
@Configuration
public class CORSConfig extends WebMvcConfigurationSupport {

    @Autowired
    private BootPlumConfig bootPlumConfig;
    /**
     * @Author wangwei
     * @Description //TODO 跨域方式一：有权限拦截时，会先执行权限拦截，导致跨域配置失败
     * @Date 15:50 2019-05-16
     * @Param [registry]
     * @return void
     **/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT","PATCH")
                .maxAge(3600);
        super.addCorsMappings(registry);
    }
//    /**
//     * @Author wangwei
//     * @Description //TODO 跨域方式二：使用filter实现跨域配置
//     * @Date 15:51 2019-05-16
//     * @Param []
//     * @return org.springframework.web.cors.CorsConfiguration
//     **/
//    private CorsConfiguration addcorsConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        List<String> list = new ArrayList<>();
//        list.add("*");
//        corsConfiguration.setAllowedOrigins(list);
//
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        return corsConfiguration;
//    }
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", addcorsConfig());
//        return new CorsFilter(source);
//    }

    /**
     * @Author wangwei
     * @Description //TODO 重写这个方法后，会导致swagger2出现问题，所以加上如下配置，具体看：https://blog.csdn.net/xtj332/article/details/80595768
     * @Date 14:05 2019-04-02
     * @Param [registry]
     * @return void
     **/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        //配置文件访问路径
        registry.addResourceHandler("/static/file/**").addResourceLocations("file:"+ bootPlumConfig.getUploadPath());
        super.addResourceHandlers(registry);
    }
}
