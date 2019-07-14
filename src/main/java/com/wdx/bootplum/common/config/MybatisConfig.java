package com.wdx.bootplum.common.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * MybatisConfig
 * </p>
 *
 * @author gqc
 * @since 2019-04-12
 */
@Configuration
public class MybatisConfig {
    /**
     * mybatis分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}