package com.example.demo_kot.jwtter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.demo_kot.util.fieldName
import java.time.Duration
import java.time.Instant
import java.util.*

class Jwtter {
    companion object {

        /**
         * 验证 token。
         */
        fun verifyToken(token: String?): Tokener {
            val decodedJWT = JWT
                    .require(Algorithm.HMAC256(JwtConstant.JWT_SECRET))
                    .build()
                    .verify(token)
            // 验证成功，写入 claims
            return Tokener(null, JwtClaims().apply { user_id = decodedJWT.getClaim(user_id.fieldName).asString() })
        }

        /**
         * 生成 token
         */
        fun generateToken(jwtClaims: JwtClaims): Tokener {
            return Tokener(
                    JWT.create()
                            .withAudience(jwtClaims.user_id.fieldName) // 签发对象
                            .withIssuedAt(Date.from(Instant.now())) // 发行时间
                            .withExpiresAt(Date.from(Instant.now().plus(Duration.ofDays(JwtConstant.TOKEN_EXPIRE)))) // 过期时间
                            .withClaim(jwtClaims.user_id.fieldName, jwtClaims.user_id) // 载荷，必须与 JwtClaims 类对应
                            .sign(Algorithm.HMAC256(JwtConstant.JWT_SECRET)),
                    jwtClaims
            )
        }
    }

}