package org.jnjeaaaat.openmarket.member.exception

import org.jnjeaaaat.openmarket.CustomException
import org.jnjeaaaat.openmarket.ErrorCode

class MemberException : CustomException {
    constructor(errorCode: ErrorCode) : super(errorCode)
    constructor(errorCode: ErrorCode, message: String) : super(errorCode, message)
}