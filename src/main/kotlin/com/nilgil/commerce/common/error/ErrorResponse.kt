package com.nilgil.commerce.common.error

data class ErrorResponse(
    val code: String,
    val message: String,
    val detail: Any? = null,
)

fun CoreException.toResponse(): ErrorResponse =
    ErrorResponse(
        code = this.type.code,
        message = this.type.message,
        detail = this.detail,
    )
