package com.example.demo_kot.controller.loginandregister

import com.baomidou.mybatisplus.extension.kotlin.KtUpdateWrapper
import com.example.demo_kot.controllerhandler.PathConstant
import com.example.demo_kot.controllerhandler.ResponseVO
import com.example.demo_kot.entity.EmailVerify
import com.example.demo_kot.entity.User
import com.example.demo_kot.exception.ControllerSelfThrowException
import com.example.demo_kot.jwtter.JwtClaims
import com.example.demo_kot.jwtter.Jwtter
import com.example.demo_kot.service.impl.EmailVerifyServiceImpl
import com.example.demo_kot.service.impl.UserServiceImpl
import org.springframework.boot.autoconfigure.mail.MailProperties
import org.springframework.http.MediaType
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@RestController
@RequestMapping(
    consumes = [MediaType.APPLICATION_JSON_VALUE],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
class ByEmailController(
    val javaMailSender: JavaMailSender,
    val mailProperties: MailProperties,
    val emailVerifyServiceImpl: EmailVerifyServiceImpl,
    val userServiceImpl: UserServiceImpl
) {


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

    @PostMapping(path = [PathConstant.LONGIN_AND_REGISTER_BY_EMAIL_SEND_EMAIL])
    fun sendEmail(@RequestBody @Validated requestVO: SendEmailRequestVO): ResponseVO<Unit> {
        // 随机 5 位数的验证码
        val randomNum = (Math.random() * 9000 + 1000).toInt()
        // 插入或更新
        val result = emailVerifyServiceImpl.saveOrUpdate(
            EmailVerify(requestVO.email, randomNum),
            KtUpdateWrapper(EmailVerify::class.java).eq(EmailVerify::email, requestVO.email)
        )

        if (!result) {
            throw ControllerSelfThrowException("修改或存储数量不应该为 0！", null)
        }


        // 存储成功后，
        // 将 邮箱发送成功 的消息进行响应
        val mailMessage = SimpleMailMessage()
        (mailMessage::setSubject)("验证码: $randomNum")
        mailMessage.setTo(requestVO.email)
        (mailMessage::setFrom)((mailProperties::getUsername)())
        (mailMessage::setText)("验证码在标题上")
        javaMailSender.send(mailMessage)

        return ResponseVO(CodeSendEmail.C2_01_01_01, "邮箱已发送, 请注意查收!", null)
    }


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
        val userId: Long?,
        val username: String?,
        val email: String?,
        val age: Int?,
        val token: String?
    )

    @PostMapping(value = [PathConstant.LONGIN_AND_REGISTER_BY_EMAIL_VERIFY_EMAIL])
    fun verifyEmail(@RequestBody @Validated requestVO: VerifyEmailRequestVO): ResponseVO<VerifyEmailResponseVO> {
        println(requestVO)
        // 查找数据库是否存在该邮箱以及相匹配的验证码。
        val isExists = emailVerifyServiceImpl.ktQuery()
            .allEq(
                mutableMapOf(
                    EmailVerify::email to requestVO.email,
                    EmailVerify::code to requestVO.code
                ),
                false
            )
            .exists()

        // 验证码正确
        if (isExists) {
            // 是否存在该邮箱用户
            val userList = userServiceImpl.ktQuery().eq(User::email, requestVO.email).list()

            when (userList.size) {

                // 不存在该邮箱用户
                0 -> {// 新建用户
                    val epochMilli = Instant.now().toEpochMilli()
                    val newUser =
                        User("还没有起名字", null, requestVO.email, 0, null, epochMilli, epochMilli)

                    // 存储用户
                    val result = userServiceImpl.save(newUser)
                    if (!result) {
                        throw ControllerSelfThrowException("存储的结果数量不应该为 0！", null)
                    }

                    // 生成 token
                    val token =
                        Jwtter.generateToken(JwtClaims().apply { user_id = newUser.id!! }).token

                    return ResponseVO(
                        CodeVerifyEmail.C2_01_02_01, "邮箱注册成功！",
                        newUser.run { VerifyEmailResponseVO(id, username, email, age, token) }
                    )
                }

                // 已存在一个该邮箱用户
                1 -> {
                    // 生成 token
                    val token =
                        Jwtter.generateToken(JwtClaims().apply {
                            user_id = userList.first().id!!
                        }).token
                    return ResponseVO(
                        CodeVerifyEmail.C2_01_02_02,
                        "邮箱登陆成功！",
                        userList.first()
                            .run { VerifyEmailResponseVO(id, username, email, age, token) })
                }

                // 存在多个该邮箱用户
                else -> {
                    return ResponseVO(CodeVerifyEmail.C2_01_02_03, "邮箱重复异常，请联系管理员！", null)
                        .withErrorLog("数据库存在多个相同邮箱：${requestVO.email}", null)
                }
            }
        }
        // 验证码不正确
        else {
            return ResponseVO(CodeVerifyEmail.C2_01_02_04, "验证码不正确！", null)
        }
    }
}