package com.example.demo_kot.controller.responsevo.loginandregister.byemail

data class VerifyEmailResponseVO(
    val userId: Long?,
    val username: String?,
    val email: String?,
    val age: Int?,
    val token: String?
)