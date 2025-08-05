package com.nilgil.commerce.order

import com.nilgil.commerce.common.error.ErrorType
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode

enum class OrderError(
    override val httpStatus: HttpStatusCode,
    override val errorCode: String,
    override val message: String,
) : ErrorType {
    INVALID_STATUS_TRANSITION(HttpStatus.CONFLICT, "O001", "주문 상태를 변경할 수 없습니다."),
}
