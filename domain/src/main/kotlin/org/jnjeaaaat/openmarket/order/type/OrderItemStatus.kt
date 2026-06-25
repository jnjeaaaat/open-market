package org.jnjeaaaat.openmarket.order.type

enum class OrderItemStatus {
    PENDING_PAYMENT,
    PAYMENT_COMPLETED,

    PREPARING,

    SHIPPING,

    DELIVERED,

    PURCHASE_CONFIRMED,

    CANCEL_REQUESTED,
    CANCELED,

    RETURN_REQUESTED,
    RETURNED,

    REFUND_REQUESTED,
    REFUNDED
}