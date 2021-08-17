package com.example.demo_kot

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
//@EnableOpenApi
//@EnableWebSecurity
@MapperScan("com.example.demo_kot.mapper")
class DemoKotApplication

fun main(args: Array<String>) {
    
    runApplication<DemoKotApplication>(*args)
}

