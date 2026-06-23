package org.jnjeaaaat.openmarket.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.DirectExchange
import org.springframework.amqp.core.ExchangeBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.QueueBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OrderPaymentExpirationRabbitMQConfig {

    companion object {
        const val EXCHANGE = "order.payment.expiration.exchange"
        const val WAIT_QUEUE = "order.payment.expiration.wait.queue"
        const val WAIT_ROUTING_KEY = "order.payment.expiration.wait"

        const val DLX = "order.payment.expiration.dlx"
        const val EXPIRED_QUEUE = "order.payment.expiration.expired.queue"
        const val EXPIRED_ROUTING_KEY = "order.payment.expiration.expired"

        private const val TTL = 10 * 60 * 1000
    }

    @Bean
    fun orderPaymentExpirationExchange(): DirectExchange {
        return ExchangeBuilder
            .directExchange(EXCHANGE)
            .durable(true)
            .build()
    }

    @Bean
    fun orderPaymentExpirationWaitQueue(): Queue {
        return QueueBuilder
            .durable(WAIT_QUEUE)
            .ttl(TTL)
            .deadLetterExchange(DLX)
            .deadLetterRoutingKey(EXPIRED_ROUTING_KEY)
            .build()
    }

    @Bean
    fun orderPaymentExpirationWaitBinding(): Binding {
        return BindingBuilder
            .bind(orderPaymentExpirationWaitQueue())
            .to(orderPaymentExpirationExchange())
            .with(WAIT_ROUTING_KEY)
    }

    @Bean
    fun orderPaymentExpirationDlx(): DirectExchange {
        return ExchangeBuilder
            .directExchange(DLX)
            .durable(true)
            .build()
    }

    @Bean
    fun orderPaymentExpirationExpiredQueue(): Queue {
        return QueueBuilder
            .durable(EXPIRED_QUEUE)
            .build()
    }

    @Bean
    fun orderPaymentExpirationExpiredBinding(): Binding {
        return BindingBuilder
            .bind(orderPaymentExpirationExpiredQueue())
            .to(orderPaymentExpirationDlx())
            .with(EXPIRED_ROUTING_KEY)
    }
}