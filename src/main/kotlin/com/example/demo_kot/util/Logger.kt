package com.example.demo_kot.util


class LoggerInstance {
    private val slf4jLogger: org.slf4j.Logger = org.slf4j.LoggerFactory.getLogger(LoggerInstance::class.java)

    fun info(code: Int?, message: String?, data: Any?, description: String?, throwable: Throwable?) {
        slf4jLogger.info("""
            > code: $code
              message: $message
              data: $data
              description: $description
              exception: ↓
        """.trimIndent(), throwable)
    }

    fun debug(code: Int?, message: String?, data: Any?, description: String?, throwable: Throwable?) {
        slf4jLogger.debug("""
            > code: $code
              message: $message
              data: $data
              description: $description
              exception: ↓
        """.trimIndent(), throwable)
    }

    fun error(code: Int?, message: String?, data: Any?, description: String?, throwable: Throwable?) {
        slf4jLogger.error("""
            > code: $code
              message: $message
              data: $data
              description: $description
              exception: ↓
        """.trimIndent(), throwable)
    }
}

val logger: LoggerInstance = LoggerInstance()