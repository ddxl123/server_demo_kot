package com.example.demo_kot.controllervo.loginandregister

import com.example.demo_kot.controllercode.loginandregister.CSendEmail
import com.example.demo_kot.controllercode.loginandregister.CVerifyEmail
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull


data class SendEmailRequestVO(
    /**
     * 邮箱
     */
    @field:Email(message = "${CSendEmail.C1_01_01_01},邮箱格式不正确!,E")
    @field:NotBlank(message = "${CSendEmail.C1_01_01_02},邮箱不能为空!,E")
    val email: String = "S",
)


data class VerifyEmailRequestVO(
    /**
     * 邮箱
     */
    @field:Email(message = "${CVerifyEmail.C1_01_02_01},邮箱格式不正确!,E")
    @field:NotBlank(message = "${CVerifyEmail.C1_01_02_02},邮箱不能为空!,E")
    var email: String = "S",

    /**
     * 邮箱验证码
     */
    @field:NotNull(message = "${CVerifyEmail.C1_01_02_03},验证码不能为空,E")
    val code: Int = 0
)

data class VerifyEmailResponseVO(
    val user_id: Long?,
    val token: String?
)
