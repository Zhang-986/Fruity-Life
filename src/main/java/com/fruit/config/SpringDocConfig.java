package com.fruit.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(this.info());
    }
    private Info info(){
        return new Info()
                .title("水果生命周期管理系统")
                .description("这是一个用于管理水果生命周期的系统，提供水果的添加、删除、查询等功能。")
                .version("1.0.0")
                .contact(new Contact(   ).name("Zhang-986&&Gyh").url("https://github.com/Zhang-986").email("zhang986aaa@gmail.com"))
                .license(new License().name("Apache 2.0").url("https://github.com/Zhang-986"))
                .summary("水果生命周期管理系统 API 文档");
    }
}
