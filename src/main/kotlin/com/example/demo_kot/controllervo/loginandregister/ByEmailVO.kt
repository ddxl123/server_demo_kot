package com.example.demo_kot.controllervo.loginandregister

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull


object CodeSendEmail {
    const val C1_01_01_01 = 1_01_01_01
    const val C1_01_01_02 = 1_01_01_02

    const val C2_01_01_01 = 2_01_01_01

}

data class SendEmailRequestVO(
    /**
     * 邮箱
     */
    @field:Email(message = "${CodeSendEmail.C1_01_01_01},邮箱格式不正确!,E")
    @field:NotBlank(message = "${CodeSendEmail.C1_01_01_02},邮箱不能为空!,E")
    val email: String = "S",
)


object CodeVerifyEmail {
    const val C1_01_02_01 = 1_01_02_01
    const val C1_01_02_02 = 1_01_02_02
    const val C1_01_02_03 = 1_01_02_03

    const val C2_01_02_01 = 2_01_02_01
    const val C2_01_02_02 = 2_01_02_02
    const val C2_01_02_03 = 2_01_02_03
    const val C2_01_02_04 = 2_01_02_04

}

data class VerifyEmailRequestVO(
    /**
     * 邮箱
     */
    @field:Email(message = "${CodeVerifyEmail.C1_01_02_01},邮箱格式不正确!,E")
    @field:NotBlank(message = "${CodeVerifyEmail.C1_01_02_02},邮箱不能为空!,E")
    var email: String = "S",

    /**
     * 邮箱验证码
     */
    @field:NotNull(message = "${CodeVerifyEmail.C1_01_02_03},验证码不能为空,E")
    val code: Int = 0
)

data class VerifyEmailResponseVO(
    val user_id: Long?,
    val token: String?
)
