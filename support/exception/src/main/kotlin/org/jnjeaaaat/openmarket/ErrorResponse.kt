package org.jnjeaaaat.openmarket

import org.springframework.http.ResponseEntity

sealed class ErrorResponse(
    val errorCode: ErrorCode
) {
    class Single(errorCode: ErrorCode, val message: String) : ErrorResponse(errorCode)
    class Multiple(errorCode: ErrorCode, val messages: List<String>) : ErrorResponse(errorCode)

    companion object {
        fun of(errorCode: ErrorCode, message: String): ResponseEntity<ErrorResponse> =
            ResponseEntity.status(errorCode.httpStatus)
                .body(Single(errorCode, message))

        fun ofArray(errorCode: ErrorCode, messages: List<String>): ResponseEntity<ErrorResponse> =
            ResponseEntity.status(errorCode.httpStatus)
                .body(Multiple(errorCode, messages))
    }
}