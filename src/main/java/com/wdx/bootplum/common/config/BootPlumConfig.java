package com.wdx.bootplum.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 * BootPlum项目 自定义配置类
 * </p>
 *
 * @author gqc
 * @since 2019-04-16
 */
@Component
@ConfigurationProperties(prefix="bootplum")
@Data
public class BootPlumConfig {
    //上传路径
    private String uploadPath;
}