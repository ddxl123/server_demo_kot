package com.example.demo_kot.interceptor

import com.example.demo_kot.exception.RequestInterceptorSelfThrowException
import com.example.demo_kot.jwtter.JwtConstant
import com.example.demo_kot.jwtter.Jwtter
import com.example.demo_kot.util.logger
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.util.regex.Pattern
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class RequestInterceptor : HandlerInterceptor {


    val passContains: Regex = Regex("swagger|v3")

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val pattern = Pattern.compile("/\\w+")
        val matcher = pattern.matcher(request.requestURL)

        // 获取到 http://xxx/yyy/zzz 中的 "/yyy"(带"/")
        if (matcher.find() && matcher.find()) {
            val group: String = matcher.group()

            // 需要直接通过的 URL
            if (group.contains(passContains)) {
                logger.info(null, null, null, "未被拦截的 URL：${request.requestURL}", null)
                return true
            }
            // 若 "/yyy" 为不需要 jwt 认证
            when (group) {
                JwtConstant.NO_JWT_URL -> return true
                JwtConstant.JWT_URL -> {
                    // 获取请求头中的 token
                    val token: String = request.getHeader("authorization").removePrefix("bearer ")
                    // 验证 token
                    Jwtter.verifyToken(token);
                    // 只有验证成功的才能通过，否则会抛出异常。
                    return true
                }
            }
        }
        throw RequestInterceptorSelfThrowException("可能是 url 不符合规范！", null)
    }
}