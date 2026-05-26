package org.jnjeaaaat.openmarket

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.*

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val message: String
) {
    INVALID_REQUEST(BAD_REQUEST, "잘못된 입력입니다."),
    INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "예상치 못한 내부 문제가 발생했습니다."),

    NOT_FOUND_MEMBER(NOT_FOUND, "존재하지 않는 회원입니다."),
    ALREADY_EXISTS_EMAIL(BAD_REQUEST, "이미 존재하는 이메일입니다."),

    NOT_FOUND_CATEGORY(NOT_FOUND, "존재하지 않는 카테고리 입니다."),
    ALREADY_EXISTS_CATEGORY(BAD_REQUEST, "이미 존재하는 카테고리입니다."),

    NOT_FOUND_PRODUCT(NOT_FOUND, "존재하지 않는 상품입니다."),
    NOT_AVAILABLE_PRODUCT(BAD_REQUEST, "구매할 수 없는 상품입니다."),
    NOT_ENOUGH_PRODUCT_STOCK(BAD_REQUEST, "상품 재고가 부족합니다."),

    NOT_FOUND_CART(NOT_FOUND, "존재하지 않는 장바구니입니다."),

    NOT_FOUND_WALLET(NOT_FOUND, "존재하지 않는 지갑 입니다."),
    CHARGE_LIMIT_EXCEEDED(BAD_REQUEST, "일일 충전 한도를 초과했습니다."),
    ;
}
