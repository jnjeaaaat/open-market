package org.jnjeaaaat.openmarket.exception

import org.jnjeaaaat.openmarket.CustomException
import org.jnjeaaaat.openmarket.ErrorCode
import org.jnjeaaaat.openmarket.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

private const val ARG_VALID_MSG = "%s 필드의(는) %s (전달된 값: %s)"

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> =
        ErrorResponse.of(e.errorCode, e.message)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodNotAllowedException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val messages = e.fieldErrors.map { fieldError ->
            ARG_VALID_MSG.format(
                fieldError.field,
                fieldError.defaultMessage,
                fieldError.rejectedValue
            )
        }

        return ErrorResponse.ofArray(ErrorCode.INVALID_REQUEST, messages)
    }
}