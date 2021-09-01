package com.example.demo_kot

import com.example.demo_kot.service.impl.EmailVerifyServiceImpl
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations

@SpringBootTest
class DemoControllerKotApplicationTests {

    @Autowired
    lateinit var serviceImpl: EmailVerifyServiceImpl

    @Autowired
    lateinit var stringRedisTemplate: StringRedisTemplate

    @Test
    fun contextLoads() {
        stringRedisTemplate.opsForValue().set("ggg", "123123")
    }
}
