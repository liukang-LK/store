package com.kakarot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class Swagger2 {


    /**
     * 配置swagger2核心配置 docket
     * 原生路径：http://localhost:8088/swagger-ui.html
     * 新皮肤路径：http://localhost:8088/doc.html
     * 也可在controller和model层用注解对接口进行更详细的说明
     */
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)      // 指定api类型为swagger2
                .apiInfo(apiInfo())                         // 用于定义api文档汇总信息
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kakarot.controller"))    // 指定controller包
                .paths(PathSelectors.any())         // 所有controller
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("干净又卫生  电商平台接口api")          // 文档页标题
                .contact(new Contact("kakarot",
                        "http:/www.liukang.space",
                        "lk941206@qq.com"))         // 联系人信息
                .description("专为干净又卫生电商平台提供的api文档")     // 详细信息
                .version("1.0.1")
                .termsOfServiceUrl("http://www.liukang.space")      // 网站地址
                .build();
    }

}
