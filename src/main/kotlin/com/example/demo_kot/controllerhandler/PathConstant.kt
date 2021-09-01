package com.example.demo_kot.controllerhandler

object PathConstant {
    /**
     * 需要通过 jwt 认证的相对 URL。
     */
    const val JWT = "/jwt"

    /**
     * 不需要通过 jwt 认证的相对 URL。
     */
    const val NO_JWT = "/no_jwt"

    const val LONGIN_AND_REGISTER_BY_EMAIL_SEND_EMAIL =
        "$NO_JWT/login_and_register_by_email/send_email"

    const val LONGIN_AND_REGISTER_BY_EMAIL_VERIFY_EMAIL =
        "$NO_JWT/login_and_register_by_email/verify_email"

    const val VERIFY_TOKEN = "$NO_JWT/verify_token";
}