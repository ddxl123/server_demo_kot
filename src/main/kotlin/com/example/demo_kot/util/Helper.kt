package com.example.demo_kot.util

object Helper {

    fun toUnderLineCase(camelCase: String) {
        camelCase.replace(Regex("[A-Z]")) {
            println(it.value)
            "_${it.value.lowercase()}"
        }.removePrefix("_")
    }
}