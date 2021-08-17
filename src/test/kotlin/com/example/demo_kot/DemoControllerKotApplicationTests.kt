package com.example.demo_kot

import com.example.demo_kot.entity.EmailVerify
import com.example.demo_kot.service.impl.EmailVerifyServiceImpl
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DemoControllerKotApplicationTests {

    @Autowired
    lateinit var serviceImpl: EmailVerifyServiceImpl

    @Test
    fun contextLoads() {
        val insert = EmailVerify()
        println(serviceImpl.baseMapper.insert(insert))
        println(insert)
    }
}
