package com.example.demo_kot.config.webmvcconfigurer

import com.example.demo_kot.interceptor.RequestInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Component
class WebMvcConfig : WebMvcConfigurer {

    @Autowired
    lateinit var requestInterceptor: RequestInterceptor;

    override fun addInterceptors(registry: InterceptorRegistry) {
        // 拦截全部
        registry.addInterceptor(requestInterceptor).addPathPatterns()
    }

}
