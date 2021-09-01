package com.example.demo_kot.jwtter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.demo_kot.interceptor.ResponseExceptionInterceptor
import com.example.demo_kot.util.fieldName
import java.time.Duration
import java.time.Instant
import java.util.*

class Jwtter {
    companion object {

        /**
         * 验证 token。
         *
         * 发生异常会直接抛给 [ResponseExceptionInterceptor.jwtException] 处理。
         *
         * @return 验证成功则会直接返回传入的 token 信息。
         */
        fun verifyToken(token: String): Tokener {

            val decodedJWT = JWT
                .require(Algorithm.HMAC256(JwtConstant.JWT_SECRET))
                .build()
                .verify(token)
            // 验证成功，写入 claims
            return Tokener(
                token,
                JwtClaims().apply { user_id = decodedJWT.getClaim(user_id.fieldName).asLong() })
        }

        /**
         * 验证并刷新 token（如果验证成功）。
         *
         * 发生异常会直接抛给 [ResponseExceptionInterceptor.jwtException] 处理。
         *
         * @return 验证成功则会直接返回刷新后的 token 信息。
         */
        fun verifyAndRefreshToken(token: String): Tokener {
            val oldTokener = verifyToken(token)
            return generateToken(JwtClaims().apply { user_id = oldTokener.jwtClaims.user_id })
        }

        /**
         * 生成 token
         *
         * 发生异常会直接抛给 [ResponseExceptionInterceptor.jwtException] 处理。
         *
         * @return 生成成功则会直接返回生成的 token 信息。
         */
        fun generateToken(jwtClaims: JwtClaims): Tokener {
            return Tokener(
                JWT.create()
                    .withAudience(jwtClaims.user_id.fieldName) // 签发对象
                    .withIssuedAt(Date.from(Instant.now())) // 发行时间
                    // 过期时间
                    .withExpiresAt(
                        Date.from(
                            Instant.now().plus(Duration.ofDays(JwtConstant.TOKEN_EXPIRE))
                        )
                    )
                    // 载荷，必须与 JwtClaims 类对应
                    .withClaim(
                        jwtClaims.user_id.fieldName,
                        jwtClaims.user_id
                    )
                    .sign(Algorithm.HMAC256(JwtConstant.JWT_SECRET)),
                jwtClaims
            )
        }
    }

}
