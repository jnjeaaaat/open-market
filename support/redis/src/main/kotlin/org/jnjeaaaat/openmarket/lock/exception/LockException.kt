package org.jnjeaaaat.openmarket.lock.exception

import org.jnjeaaaat.openmarket.CustomException
import org.jnjeaaaat.openmarket.ErrorCode

class LockException : CustomException {
    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(errorCode: ErrorCode, message: String) : super(errorCode, message)
}