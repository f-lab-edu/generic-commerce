package com.nilgil.commerce.common.error

import org.springframework.boot.logging.LogLevel

data class CoreException(
    val type: ErrorType = CoreError.UNKNOWN_ERROR,
    override val cause: Throwable? = null,
    val logLevel: LogLevel = LogLevel.ERROR,
    val detail: Any? = null,
) : RuntimeException(type.message, cause)
