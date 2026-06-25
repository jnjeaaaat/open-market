package org.jnjeaaaat.openmarket.order.event.handler

import org.jnjeaaaat.openmarket.config.OrderPaymentExpirationRabbitMQConfig.Companion.EXCHANGE
import org.jnjeaaaat.openmarket.config.OrderPaymentExpirationRabbitMQConfig.Companion.WAIT_ROUTING_KEY
import org.jnjeaaaat.openmarket.message.OrderPaymentExpirationMessage
import org.jnjeaaaat.openmarket.order.event.OrderCreatedEvent
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class OrderCreatedEventHandler(
    private val rabbitTemplate: RabbitTemplate
) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handle(event: OrderCreatedEvent) {
        rabbitTemplate.convertAndSend(
            EXCHANGE,
            WAIT_ROUTING_KEY,
            OrderPaymentExpirationMessage(
                event.orderId
            )
        )
    }

}