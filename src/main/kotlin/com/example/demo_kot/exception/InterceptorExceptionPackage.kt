package com.example.demo_kot.exception

class InterceptorExceptionPackage(override val message: String?, override val cause: Throwable?) : BaseExceptionPackage(message, cause) {

    val incorrectUrlException: InterceptorExceptionPackage? = null

}
