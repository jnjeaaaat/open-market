package org.jnjeaaaat.openmarket

abstract class CustomException(
    val errorCode: ErrorCode,
    override val message: String = errorCode.message
) : RuntimeException(message)