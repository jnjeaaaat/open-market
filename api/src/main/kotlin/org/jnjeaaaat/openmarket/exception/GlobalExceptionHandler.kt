package org.jnjeaaaat.openmarket.exception

import org.jnjeaaaat.openmarket.CustomException
import org.jnjeaaaat.openmarket.ErrorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<ErrorResponse> =
        ErrorResponse.of(e.errorCode, e.message)

}