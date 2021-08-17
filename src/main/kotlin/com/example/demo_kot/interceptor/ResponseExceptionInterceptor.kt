package com.example.demo_kot.interceptor

import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException
import com.example.demo_kot.controllerhandler.ResponseVO
import com.example.demo_kot.exception.ControllerSelfThrowException
import com.example.demo_kot.exception.RequestInterceptorSelfThrowException
import org.springframework.dao.DataAccessException
import org.springframework.mail.MailException
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ResponseExceptionInterceptor {

    companion object {
        const val C3010101 = 3010101
        const val C3010201 = 3010201
        const val C3010301 = 3010301
        const val C3010401 = 3010401
        const val C3010501 = 3010501
        const val C3010502 = 3010502
        const val C3010602 = 3010602
        const val C3010701 = 3010701
        const val C3010801 = 3010801
    }

    /**
     * 设置未知错误。
     */
    fun setResponseException(
        code: Int,
        description: String,
        throwable: Throwable?
    ): ResponseVO<Unit> {
        return ResponseVO<Unit>(
            code = code,
            message = "错误($code), 请咨询管理员",
            data = null
        ).withErrorLog(description, throwable)
    }

    /**
     * 拦截其他未知异常。
     */
    @ExceptionHandler(Throwable::class)
    fun otherExceptionHandler(throwable: Throwable): ResponseVO<Unit> {
        return setResponseException(C3010101, "未知异常！", throwable)
    }

    /**
     * Validated 失败的处理。
     * 其中包含：
     * 1. 验证结果不匹配的处理。
     * 2. 验证发生异常的处理。
     */
    @ExceptionHandler(value = [BindException::class])
    fun bindExceptionHandler(bindException: BindException): ResponseVO<Unit> {
        try {
            val errMessage: String = bindException.allErrors.first().defaultMessage!!

            // 用 "," 将 errMessage 分割成 code 和 message
            val split: List<String> = errMessage.split(",")
            val code: Int = split[0].toInt()
            val logType: String = split[2]

            // 响应解析成功的 code + message
            return ResponseVO<Unit>(code, split[1], null).also {
                val description = "请求参数验证结果为不正确的输出。"
                val throwable = bindException.cause
                when (logType) {
                    "N" -> {
                    }
                    "I" -> it.withInfoLog(description, throwable)
                    "D" -> it.withDebugLog(description, throwable)
                    "E" -> it.withErrorLog(description, throwable)
                    else -> it.withErrorLog("logType 不正确！", null)
                }
            }
        } catch (throwable: Throwable) {
            return setResponseException(
                C3010201, """${"\n"}
                请求参数的验证，出现异常！
                可能原因如下：
                    1. bindException 中不存在的 error!
                    2. 存在 bindException 的 error，但未指定 errMessage!
                    3. 指定的 errMessage 不规范！
            """.trimIndent(), throwable
            )
        }
    }

    /**
     * MybatisPlus 的 service、mapper 等对数据库的操作出现的异常。
     */
    @ExceptionHandler(MybatisPlusException::class)
    fun mybatisPlusExceptionHandler(mybatisPlusException: MybatisPlusException): ResponseVO<Unit> {
        return setResponseException(
            C3010301,
            "可能是 MybatisPlus 的 service、mapper 等对数据库的操作出现的异常!",
            mybatisPlusException
        )
    }


    /**
     * 邮箱发送异常。
     */
    @ExceptionHandler(MailException::class)
    fun mailExceptionHandler(mailException: MailException): ResponseVO<Unit> {
        return setResponseException(C3010401, "邮箱发送异常！", mailException)
    }


    /**
     * jwt 异常。包含 token 生成、验证异常。
     */
    @ExceptionHandler(JWTCreationException::class, JWTVerificationException::class)
    fun baseJwtException(jwtException: Exception): ResponseVO<Unit> {
        return if (jwtException is TokenExpiredException) {
            // 无需输出日志
            ResponseVO<Unit>(C3010501, "用户过期，请重新登陆！", null)
        } else {
            setResponseException(
                C3010502, """
                jwt 发生异常，可能的原因如下：
                    1. jwt 的生成发生异常。
                    2. jwt 的验证发生异常。例如，Token 验证不通过！用户发出了错误 Token ，可能存在应用数据被篡改的操作！
                    2. 未知异常！
            """.trimIndent(), jwtException
            )
        }
    }

    /**
     * sql 异常
     */
    @ExceptionHandler(DataAccessException::class)
    fun sqlException(dataAccessException: DataAccessException): ResponseVO<Unit> {
        return setResponseException(
            C3010602, """
                错误的原因可能如下：
                    1. "数据库入口异常，可能是 实体 与 数据库的数据表 不相互对应！
            """.trimIndent(),
            dataAccessException
        )
    }

    /**
     * 请求拦截器【自抛】异常。
     */
    @ExceptionHandler(RequestInterceptorSelfThrowException::class)
    fun requestInterceptorSelfThrowExceptionHandler(requestInterceptorSelfThrowException: RequestInterceptorSelfThrowException): ResponseVO<Unit> {
        return setResponseException(
            C3010701,
            "自抛异常(RequestInterceptorSelfThrowException): " + (requestInterceptorSelfThrowException.message
                ?: "自抛异常的 description 为 null"),
            requestInterceptorSelfThrowException
        )
    }

    /**
     * controller【自抛】异常。
     */
    @ExceptionHandler(ControllerSelfThrowException::class)
    fun controllerSelfThrowExceptionHandler(controllerSelfThrowException: ControllerSelfThrowException): ResponseVO<Unit> {
        return setResponseException(
            C3010801,
            "自抛异常(ControllerSelfThrowException): " + (controllerSelfThrowException.message
                ?: "自抛异常的 description 为 null"),
            controllerSelfThrowException
        )
    }
}