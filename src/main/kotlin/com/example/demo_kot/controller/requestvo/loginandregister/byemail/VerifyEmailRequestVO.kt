package com.example.demo_kot.controller.requestvo.loginandregister.byemail

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class VerifyEmailRequestVO(
    /**
     * 邮箱
     */
    @field:Email(message = "$C2020101,邮箱格式不正确!,E")
    @field:NotBlank(message = "$C2020102,邮箱不能为空!,E")
    var email: String = "S",

    /**
     * 邮箱验证码
     */
    @field:NotNull(message = "$C2020201,验证码不能为空,E")
    val code: Int = 0
) {
    companion object {
        const val C2020101 = 2020101
        const val C2020102 = 2020102
        const val C2020201 = 2020201
    }
}