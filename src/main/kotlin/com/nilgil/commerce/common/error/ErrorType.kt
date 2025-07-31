package com.nilgil.commerce.common.error

import org.springframework.http.HttpStatus

interface ErrorType {
    val status: HttpStatus
    val code: String
    val message: String
}
