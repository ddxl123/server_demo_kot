package com.example.demo_kot.util


class LoggerInstance {
    private val slf4jLogger: org.slf4j.Logger =
        org.slf4j.LoggerFactory.getLogger(LoggerInstance::class.java)

    fun info(
        code: Int?,
        message: String?,
        data: Any?,
        description: String?,
        throwable: Throwable?
    ) {
        slf4jLogger.info(
            """${"\n"}
            > code: $code
              message: $message
              data: $data
              description: $description
              exception: ${if (throwable == null) null else "↓"}
        """.trimIndent(), throwable
        )
    }

    fun debug(
        code: Int?,
        message: String?,
        data: Any?,
        description: String?,
        throwable: Throwable?
    ) {
        slf4jLogger.debug(
            """${"\n"}
            > code: $code
              message: $message
              data: $data
              description: $description
              exception: ${if (throwable == null) null else "↓"}
        """.trimIndent(), throwable
        )
    }

    fun error(
        code: Int?,
        message: String?,
        data: Any?,
        description: String?,
        throwable: Throwable?
    ) {
        slf4jLogger.error(
            """${"\n"}
            > code: $code
              message: $message
              data: $data
              description: $description
              exception: ${if (throwable == null) null else "↓"}
        """.trimIndent(), throwable
        )
    }
}

val logger: LoggerInstance = LoggerInstance()