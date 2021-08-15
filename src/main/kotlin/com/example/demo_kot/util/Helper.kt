package com.example.demo_kot.util

object Helper {

    /**
     * 将 AbCd 或 abCd 转化为 ab_cd
     */
    fun toUnderLineCase(camelCase: String): String {
        return camelCase.replace(Regex("[A-Z]")) {
            "_${it.value.lowercase()}"
        }.removePrefix("_")
    }
}