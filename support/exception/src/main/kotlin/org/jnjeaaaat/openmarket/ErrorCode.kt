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
    EMPTY_CART(UNPROCESSABLE_ENTITY, "장바구니가 비어 있습니다."),
    LEAST_ONE_CART_ITEM(BAD_REQUEST, "최소 한 개 이상의 장바구니 아이템이 필요합니다."),
    UNMATCH_CART_ITEM_COUNT(BAD_REQUEST, "장바구니 아이템 수와 요청된 아이템 수가 일치하지 않습니다."),

    NOT_FOUND_WALLET(NOT_FOUND, "존재하지 않는 지갑 입니다."),
    CHARGE_LIMIT_EXCEEDED(BAD_REQUEST, "일일 충전 한도를 초과했습니다."),

    NOT_FOUND_ORDER(NOT_FOUND, "존재하지 않는 주문입니다."),
    NOT_PENDING_ORDER(BAD_REQUEST, "결제 대기 중인 주문이 아닙니다."),
    PAYMENT_NOT_EXPIRED(BAD_REQUEST, "결제 만료 시간이 지나지 않았습니다.")
    ;
}
