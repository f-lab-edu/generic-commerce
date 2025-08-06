package com.nilgil.commerce.common.error

data class ErrorResponse(
    val errorCode: String,
    val message: String,
    val detail: Any? = null,
) {
    companion object {
        fun from(e: CoreException) =
            ErrorResponse(
                errorCode = e.errorType.errorCode,
                message = e.errorType.message,
                detail = e.errorDetail,
            )
    }
}
