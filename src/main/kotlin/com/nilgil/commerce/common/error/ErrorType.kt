package com.nilgil.commerce.common.error

import org.springframework.http.HttpStatusCode

/**
 * 클라이언트와의 약속으로 정의된 에러이며 정적인 요소들로 이루어져 있습니다.
 * 동적인 데이터를 클라이언트에 제공하고자 하는 경우 [CoreException.errorDetail] 를 사용합니다.
 */
interface ErrorType {
    val httpStatus: HttpStatusCode
    val errorCode: String
    val message: String
}
