package com.example.demo_kot.controller

import com.example.demo_kot.jwtter.JwtConstant

object PathConstant {
    const val LONGIN_AND_REGISTER_BY_EMAIL_SEND_EMAIL =
        JwtConstant.NO_JWT_URL + "/login_and_register_by_email/send_email"

    const val LONGIN_AND_REGISTER_BY_EMAIL_VERIFY_EMAIL =
        JwtConstant.NO_JWT_URL + "/login_and_register_by_email/verify_email"
}