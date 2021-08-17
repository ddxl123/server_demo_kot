package com.example.demo_kot.controller.test

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DemoController {


    @GetMapping("/a")
    fun a(): Int {
        return 123;
    }
}