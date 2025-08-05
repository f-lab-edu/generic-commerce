package com.nilgil.commerce.common.error

import org.springframework.boot.logging.LogLevel

/**
 * [Throwable.message]를 final로 선언하여 확장을 막으며 [ErrorType.message]를 사용하도록 고정합니다.
 *
 * 클라이언트에 응답되는 메세지는 [ErrorType.message] 고정입니다.
 * 응답에 부가적인 내용을 담고자 한다면 [errorDetail]을 사용합니다.
 *
 * 로그에 남는 메세지는 [logMessage]를 지정하지 않으면 기본적으로 [ErrorType.message]가 사용됩니다.
 */
open class CoreException(
    val errorType: ErrorType,
    val errorDetail: Any? = null,
    override val cause: Throwable? = null,
    val logLevel: LogLevel = LogLevel.ERROR,
    val logMessage: () -> String = { errorType.message },
) : RuntimeException(errorType.message, cause) {
    final override val message: String = errorType.message
}
