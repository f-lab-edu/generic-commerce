package com.nilgil.commerce.common.error

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

/**
 * 공통으로 사용될 수 있는 에러입니다.
 * [HttpStatus] 단위로 기본 에러를 제공하며, 각 도메인에서 명세하지 않을 수준의 에러인 경우 사용합니다.
 */
enum class CoreError(
    override val httpStatus: HttpStatusCode,
    override val errorCode: String,
    override val message: String,
) : ErrorType {
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "C400", "요청 값이 올바르지 않습니다."),
    AUTHENTICATION_ERROR(HttpStatus.UNAUTHORIZED, "C401", "인증되지 않은 사용자입니다."),
    AUTHORIZATION_ERROR(HttpStatus.FORBIDDEN, "C403", "접근 권한이 없습니다."),
    NOT_FOUND_ERROR(HttpStatus.NOT_FOUND, "C404", "리소스를 찾을 수 없습니다."),
    NOT_ALLOWED_METHOD_ERROR(HttpStatus.METHOD_NOT_ALLOWED, "C405", "허용되지 않은 Http Method 입니다."),
    CONFLICT_ERROR(HttpStatus.CONFLICT, "C409", "충돌되는 리소스가 존재합니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C500", "알 수 없는 오류가 발생했습니다."),
    ;

    companion object {
        fun findByStatus(status: HttpStatusCode): CoreError? = CoreError.entries.firstOrNull { it.httpStatus == status }
    }
}
