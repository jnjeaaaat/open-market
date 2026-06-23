package org.jnjeaaaat.openmarket.order.exception

import org.jnjeaaaat.openmarket.CustomException
import org.jnjeaaaat.openmarket.ErrorCode

class OrderException : CustomException {
    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(errorCode: ErrorCode, message: String) : super(errorCode, message)
}