package com.nilgil.commerce.common.error

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.logging.LogLevel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log: Logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(CoreException::class)
    fun handleCoreException(e: CoreException): ResponseEntity<ErrorResponse> {
        log.logOnLevel(
            level = e.logLevel,
            message = "[ERR-${e.type.code}] ${e.message}",
            throwable = e,
        )
        return ResponseEntity(e.toResponse(), e.type.status)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> =
        handleCoreException(CoreException(type = CoreError.UNKNOWN_ERROR, cause = e))

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: Exception): ResponseEntity<ErrorResponse> =
        handleCoreException(CoreException(type = CoreError.VALIDATION_ERROR, cause = e))

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(e: Exception): ResponseEntity<ErrorResponse> =
        handleCoreException(CoreException(type = CoreError.CONFLICT_ERROR, cause = e))

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(e: Exception): ResponseEntity<ErrorResponse> =
        handleCoreException(CoreException(type = CoreError.VALIDATION_ERROR, cause = e))
}

private fun Logger.logOnLevel(
    level: LogLevel,
    message: String,
    throwable: Throwable? = null,
) {
    when (level) {
        LogLevel.ERROR -> error(message, throwable)
        LogLevel.WARN -> warn(message, throwable)
        LogLevel.INFO -> info(message, throwable)
        LogLevel.DEBUG -> debug(message, throwable)
        LogLevel.TRACE -> trace(message, throwable)
        else -> error(message, throwable)
    }
}
