package com.example.demo_kot.controllervo.verifytoken


data class VerifyTokenRequestVO(
    val token: String = "S",
)

data class VerifyTokenResponseVO(
    val new_token: String = "S",
)