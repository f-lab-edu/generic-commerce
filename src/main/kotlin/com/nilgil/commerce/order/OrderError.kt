package com.nilgil.commerce.order

import com.nilgil.commerce.common.error.ErrorType
import org.springframework.http.HttpStatus

enum class OrderError(
    override val status: HttpStatus,
    override val code: String,
    override val message: String,
) : ErrorType {
    INVALID_STATUS_TRANSITION(HttpStatus.CONFLICT, "O001", "주문 상태를 변경할 수 없습니다."),
    ALREADY_HAS_ACTIVE_ORDER(HttpStatus.CONFLICT, "C002", "사용자에게 이미 활성화된 주문이 존재합니다."),
    INVALID_ITEM(HttpStatus.BAD_REQUEST, "C003", "유효하지 않은 상품입니다."),
}

data class InvalidLineInfo(
    val id: Long,
    val code: InvalidType,
)

enum class InvalidType {
    NOT_FOUND,
    STOCK_NOT_ENOUGH,
}
