package com.example.demo_kot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
//@EnableOpenApi
//@EnableWebSecurity
//@MapperScan(basePackages = [PackageNameConstant.MAPPER_PACKAGE])
class DemoKotApplication

fun main(args: Array<String>) {
    
    runApplication<DemoKotApplication>(*args)
}

