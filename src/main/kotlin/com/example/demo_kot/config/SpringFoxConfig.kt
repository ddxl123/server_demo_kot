package com.example.demo_kot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket

/**
 * @author 10338
 */
@Configuration
class SpringFoxConfig {
    @Bean
    fun createRestApi(): Docket {
        println("Api 接口文档可以访问网址: http://localhost:8080/swagger-ui/")
        return Docket(DocumentationType.OAS_30)
                .enable(true)
                .apiInfo(apiInfo())
                .select()
                .build()
    }

    @Bean
    fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("RESTful APIs")
                .description("无描述")
                .contact(Contact("联系邮箱: 1033839760@qq.com", "", "1033839760@qq.com"))
                .version("1.0")
                .build()
    }
}