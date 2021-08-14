package com.example.demo_kot.jwtter

object JwtConstant {

    /**
     * jwt 加盐的密钥
     */
    const val JWT_SECRET = "jwt_secret"

    /**
     * token 有效期
     */
    const val TOKEN_EXPIRE = 7L

    /**
     * 需要通过 jwt 认证的相对 URL。
     */
    const val JWT_URL = "/jwt"

    /**
     * 不需要通过 jwt 认证的相对 URL。
     */
    const val NO_JWT_URL = "/no_jwt"
}
