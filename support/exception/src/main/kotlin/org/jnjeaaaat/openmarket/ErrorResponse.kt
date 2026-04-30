package org.jnjeaaaat.openmarket

import org.springframework.http.ResponseEntity

class ErrorResponse(
    val errorCode: ErrorCode,
    val message: String
) {
    companion object {
        fun of(errorCode: ErrorCode, message: String): ResponseEntity<ErrorResponse> =
            ResponseEntity.status(errorCode.httpStatus)
                .body(ErrorResponse(errorCode, message))
    }
}