package com.example.demo_kot.exception

import kotlin.reflect.full.declaredMemberProperties


abstract class BaseExceptionPackage(message: String?, cause: Throwable?) : Exception(
        message ?: """
                可能的原因：
                    1. message 为 null。
                    2. 获取 throwable 信息不应该从 BaseExceptionPackage 的子类自身中获取，而应该从其子类自身的一系列 Exceptions 字段获取！
            """.trimIndent(),
        cause,
) {


    /**
     * 返回当前存在的 Exception 字段。
     * @return 当返回 null 时，表示不存在 Exception 字段。
     */
    inline fun <reified F : BaseExceptionPackage> existException(): F? {
        return this.javaClass.kotlin.declaredMemberProperties.find { it ->
            val kField: Any? = it.get(this)
            kField != null && kField is F
        }?.get(this) as F?
    }
}
