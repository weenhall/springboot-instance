package com.ween.swaggerdemo.config;

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
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ween.swaggerdemo.controller"))
                .build();
    }

    /**
     * API信息
     * @return
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("xx系统API文档")
                .description("文档相关信息:")
                .termsOfServiceUrl("localhost:8080/about.html")//服务条款
                .contact(new Contact("weenall","github.io/weenhall","weenhallwu@gmail.com"))
                .license("Apache 2.0")//许可证
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")//许可网址
                .version("0.1.1")
                .build();
    }
}
