package com.nilgil.commerce.common.error

import org.springframework.http.HttpStatus

enum class CoreError(
    override val status: HttpStatus,
    override val code: String,
    override val message: String,
) : ErrorType {
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "알 수 없는 오류가 발생했습니다."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "C002", "요청 값이 올바르지 않습니다."),
    AUTHENTICATION_ERROR(HttpStatus.UNAUTHORIZED, "C003", "인증되지 않은 사용자입니다."),
    AUTHORIZATION_ERROR(HttpStatus.FORBIDDEN, "C004", "접근 권한이 없습니다."),
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "C005", "리소스를 찾을 수 없습니다."),
    CONFLICT_ERROR(HttpStatus.CONFLICT, "C006", "충돌되는 리소스가 존재합니다."),
}
