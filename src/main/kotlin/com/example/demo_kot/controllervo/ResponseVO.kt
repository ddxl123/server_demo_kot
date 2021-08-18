package com.example.demo_kot.controllervo

import com.example.demo_kot.util.logger


/**
 * @param code 响应码。
 * @param message 响应消息。
 * @param data 响应的数据。
 */
data class ResponseVO<out T>(
    val code: Int,
    val message: String,
    val data: T?
) {
    /**
     * 是否在响应的同时输出日志。
     */
    fun withInfoLog(description: String?, throwable: Throwable?): ResponseVO<T> {
        logger.info(code, message, data, description, throwable);
        return this
    }

    /**
     * 是否在响应的同时输出日志。
     */
    fun withDebugLog(description: String?, throwable: Throwable?): ResponseVO<T> {
        logger.debug(code, message, data, description, throwable);
        return this
    }

    /**
     * 是否在响应的同时输出日志。
     */
    fun withErrorLog(description: String?, throwable: Throwable?): ResponseVO<T> {
        logger.error(code, message, data, description, throwable);
        return this
    }
}
