package org.jnjeaaaat.openmarket.order.event.consumer

import org.jnjeaaaat.openmarket.config.OrderPaymentExpirationRabbitMQConfig.Companion.EXPIRED_QUEUE
import org.jnjeaaaat.openmarket.message.OrderPaymentExpirationMessage
import org.jnjeaaaat.openmarket.order.usecase.ExpirePendingOrderUseCase
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
class OrderPaymentExpirationConsumer(
    private val expirePendingOrderUseCase: ExpirePendingOrderUseCase
) {

    @RabbitListener(queues = [EXPIRED_QUEUE])
    fun consume(message: OrderPaymentExpirationMessage) {
        expirePendingOrderUseCase(message.orderId)
    }

}