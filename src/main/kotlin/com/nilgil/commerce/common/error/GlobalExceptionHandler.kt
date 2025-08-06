package com.nilgil.commerce.common.error

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.logging.LogLevel
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 모든 예외를 [CoreException]으로 래핑 하여 핸들링합니다.
 *
 * 명시적으로 핸들링하지 않은 예외들의 경우 일괄적으로 [handleException]에서 핸들링합니다.
 * 해당 예외에서 상태 코드 정보를 추출할 수 있고, [CoreError]에 해당 상태 코드와 매핑되는 에러가 존재하는 경우 해당 에러가 사용되며,
 * 그 외 [CoreError.INTERNAL_SERVER_ERROR]가 사용되어 500 상태 코드로 응답됩니다.
 * 이는 [CoreError]에 존재하지 않는 상태 코드에 대해 클라이언트에게 표현하지 않는다는 의미를 가집니다.
 *
 * 현 클래스에서 래핑 된 경우 [CoreException.cause]를 사용하여 실제 발생 위치부터 StackTrace를 남깁니다.
 */
@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = KotlinLogging.logger {}

    @ExceptionHandler(CoreException::class)
    fun handleCoreException(e: CoreException): ResponseEntity<ErrorResponse> = logAndRespond(e)

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        val httpStatusCode = resolveHttpStatusCode(e)
        val errorType = determineErrorType(httpStatusCode)
        return wrapAndProcess(e, errorType)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> =
        wrapAndProcess(e, CoreError.VALIDATION_ERROR, LogLevel.WARN)

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(e: IllegalStateException): ResponseEntity<ErrorResponse> =
        wrapAndProcess(e, CoreError.CONFLICT_ERROR, LogLevel.WARN)

    private fun wrapAndProcess(
        e: Exception,
        errorType: CoreError,
        logLevel: LogLevel = LogLevel.ERROR,
    ): ResponseEntity<ErrorResponse> =
        logAndRespond(
            CoreException(
                errorType = errorType,
                cause = e,
                logLevel = logLevel,
            ),
        )

    private fun logAndRespond(e: CoreException): ResponseEntity<ErrorResponse> {
        log.logOnLevel(
            level = e.logLevel,
            message = e.logMessage,
            throwable = determineThrowableForStackTrace(e),
        )
        return ResponseEntity(ErrorResponse.from(e), e.errorType.httpStatus)
    }

    private fun determineThrowableForStackTrace(e: CoreException): Throwable? =
        if (e.isWrappedInHandler) {
            e.cause
        } else {
            e
        }

    private fun resolveHttpStatusCode(e: Throwable): HttpStatusCode {
        if (e is org.springframework.web.ErrorResponse) {
            return e.statusCode
        }

        e::class.java.getAnnotation(ResponseStatus::class.java)?.let {
            return it.code
        }

        return HttpStatus.INTERNAL_SERVER_ERROR
    }

    private fun determineErrorType(httpStatusCode: HttpStatusCode): CoreError =
        CoreError.findByStatus(httpStatusCode) ?: CoreError.INTERNAL_SERVER_ERROR
}

private val CoreException.isWrappedInHandler: Boolean
    get() {
        val wrappedAt = this.stackTrace.firstOrNull()?.className
        return wrappedAt == GlobalExceptionHandler::class.java.name
    }

private fun KLogger.logOnLevel(
    level: LogLevel,
    message: () -> String,
    throwable: Throwable? = null,
) {
    when (level) {
        LogLevel.FATAL -> error(throwable, message)
        LogLevel.ERROR -> error(throwable, message)
        LogLevel.WARN -> warn(throwable, message)
        LogLevel.INFO -> info(throwable, message)
        LogLevel.DEBUG -> debug(throwable, message)
        LogLevel.TRACE -> trace(throwable, message)
        LogLevel.OFF -> {}
    }
}
