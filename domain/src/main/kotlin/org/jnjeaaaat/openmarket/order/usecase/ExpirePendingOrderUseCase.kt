package org.jnjeaaaat.openmarket.order.usecase

import org.jnjeaaaat.openmarket.ErrorCode
import org.jnjeaaaat.openmarket.order.exception.OrderException
import org.jnjeaaaat.openmarket.order.repository.OrderRepository
import org.jnjeaaaat.openmarket.order.type.OrderStatus
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ExpirePendingOrderUseCase(
    private val orderRepository: OrderRepository
) {

    @Transactional
    operator fun invoke(orderId: Long) {
        val order = orderRepository.findByIdOrNull(orderId)
            ?: throw OrderException(ErrorCode.NOT_FOUND_ORDER)

        if (order.status != OrderStatus.PENDING_PAYMENT) {
            throw OrderException(ErrorCode.NOT_PENDING_ORDER)
        }

        if (order.paymentExpiredAt.isAfter(LocalDateTime.now())) {
            throw OrderException(ErrorCode.PAYMENT_NOT_EXPIRED)
        }

        order.cancelByPaymentExpiration()

        order.orderItems.forEach { orderItem ->
            orderItem.product.releaseReservedStock(orderItem.quantity)
        }
    }
}