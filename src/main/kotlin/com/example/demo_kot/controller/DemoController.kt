package com.example.demo_kot.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController {

    @GetMapping("/a")
    fun a() {
        println("a fun");
    }
}