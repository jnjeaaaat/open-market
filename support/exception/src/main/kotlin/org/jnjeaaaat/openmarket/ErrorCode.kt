package org.jnjeaaaat.openmarket

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val message: String
) {
    INVALID_REQUEST(BAD_REQUEST, "잘못된 입력입니다."),
    INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "예상치 못한 내부 문제가 발생했습니다.")

    ;
}