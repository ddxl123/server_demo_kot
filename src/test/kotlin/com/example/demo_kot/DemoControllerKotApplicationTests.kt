package com.example.demo_kot

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import kotlin.reflect.full.declaredMemberProperties

@SpringBootTest
class DemoControllerKotApplicationTests {

    val a: A = A();

    @Test
    fun contextLoads() {
        a.f();
    }
}

open class ABase {
    val ab1: Int = 0;
    private val ab2: Int = 10;
    fun f() {
        println(this.javaClass.kotlin.declaredMemberProperties.elementAt(2).get(this));
    }
}

class A : ABase() {
    val a1: Int? = null;
    val a2: Int = 111;
    val a3: Int = 10;
}
