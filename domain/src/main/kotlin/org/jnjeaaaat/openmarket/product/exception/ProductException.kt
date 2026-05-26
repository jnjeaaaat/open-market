package org.jnjeaaaat.openmarket.product.exception

import org.jnjeaaaat.openmarket.CustomException
import org.jnjeaaaat.openmarket.ErrorCode

class ProductException : CustomException {
    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(errorCode: ErrorCode, message: String) : super(errorCode, message)
}
