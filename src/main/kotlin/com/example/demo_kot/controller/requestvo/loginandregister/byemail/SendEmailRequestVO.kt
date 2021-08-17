package com.example.demo_kot.controller.requestvo.loginandregister.byemail

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank


data class SendEmailRequestVO(
    /**
     * 邮箱
     */
    @field:Email(message = "$C2010101,邮箱格式不正确!,E")
    @field:NotBlank(message = "$C2010102,邮箱不能为空!,E")
    val email: String = "S",
) {
    companion object {
        const val C2010101 = 2010101
        const val C2010102 = 2010102
    }
}