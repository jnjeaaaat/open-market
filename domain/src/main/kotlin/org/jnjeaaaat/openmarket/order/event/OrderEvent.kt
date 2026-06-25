package org.jnjeaaaat.openmarket.order.event

import org.jnjeaaaat.openmarket.common.DomainEvent
import java.time.LocalDateTime

sealed interface OrderEvent : DomainEvent

data class OrderCreatedEvent(
    val orderId: Long,
    val memberId: Long,
    val paymentExpiredAt: LocalDateTime
) : OrderEvent