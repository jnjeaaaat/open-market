package org.jnjeaaaat.openmarket.orderItem.type

enum class OrderStatus {
    PAYMENT_COMPLETED, // 주문 완료
    SHIPPING, // 배송 중
    DELIVERED, // 배송 완료
    CONFIRMED, // 주문 확정
    RETURNED // 주문 취소
}