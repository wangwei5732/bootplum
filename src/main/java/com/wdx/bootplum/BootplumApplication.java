package com.wdx.bootplum;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2
@MapperScan("com.wdx.bootplum.*.mapper")
public class BootplumApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootplumApplication.class, args);
        System.err.println("启动成功");
    }

}
