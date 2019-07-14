package com.wdx.bootplum.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile({"dev"})
public class Swagger2 {
    /**
     * 添加摘要信息(Docket)
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.wdx.bootplum.system.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("bootplum_接口文档")
                .description("描述：用于管理集团旗下公司的人员信息,具体包括XXX,XXX模块...")
                .termsOfServiceUrl("http://www.mgdaas.com/")
                .contact(new Contact("wangwei","www.baidu.com","wangw@mgdaas.com"))
                .version("1.0")
                .build();
    }
}
