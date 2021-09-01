package com.example.demo_kot.controller.verifytoken

import com.example.demo_kot.controllercode.verifytoken.CVerifyToken
import com.example.demo_kot.controllerhandler.PathConstant
import com.example.demo_kot.controllervo.ResponseVO
import com.example.demo_kot.controllervo.verifytoken.VerifyTokenRequestVO
import com.example.demo_kot.controllervo.verifytoken.VerifyTokenResponseVO
import com.example.demo_kot.jwtter.Jwtter
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class VerifyTokenController {

    @PostMapping(PathConstant.VERIFY_TOKEN)
    fun verifyToken(@RequestBody @Validated requestVO: VerifyTokenRequestVO): ResponseVO<VerifyTokenResponseVO> {
        // 验证并刷新 token。验证失败则抛异常，在拦截器中被捕获并响应。
        val newTokener = Jwtter.verifyAndRefreshToken(requestVO.token)
        return ResponseVO(
            CVerifyToken.C2_02_01_01, "验证用户成功！",
            VerifyTokenResponseVO(newTokener.token)
        )
    }
}