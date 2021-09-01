package com.example.demo_kot.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate


@Configuration
//@EnableCaching // 这个注解实际上是针对缓存的注解，应用在 redis 上的原因是将缓存的结果存入 redis 数据库中而不是存入 springboot 缓存中。
class RedisConfig {

    /**
     *  [LettuceConnectionFactory] 可以查阅 jedis 和 lettuce 的区别。
     *
     *  [StringRedisTemplate] 可以查阅 [RedisTemplate] 与 [StringRedisTemplate] 的区别。
     */
    @Bean
    fun stringRedisTemplate(factory: LettuceConnectionFactory): StringRedisTemplate {
        val stringRedisTemplate = StringRedisTemplate()
        (stringRedisTemplate::setConnectionFactory)(factory)
        return stringRedisTemplate
    }
}
