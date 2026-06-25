package org.jnjeaaaat.openmarket.order.usecase

import org.jnjeaaaat.openmarket.ErrorCode
import org.jnjeaaaat.openmarket.cart.command.toOrderItem
import org.jnjeaaaat.openmarket.cart.repository.CartItemRepository
import org.jnjeaaaat.openmarket.cart.usecase.component.CartItemValidator.validateCartItemsMatchCount
import org.jnjeaaaat.openmarket.cart.usecase.component.CartItemValidator.validateOrderableCartItems
import org.jnjeaaaat.openmarket.lock.annotation.DistributedMultiLock
import org.jnjeaaaat.openmarket.member.exception.MemberException
import org.jnjeaaaat.openmarket.member.repository.MemberRepository
import org.jnjeaaaat.openmarket.order.command.CreateOrderCommand
import org.jnjeaaaat.openmarket.order.command.CreateOrderResult
import org.jnjeaaaat.openmarket.order.command.toEntity
import org.jnjeaaaat.openmarket.order.command.toResult
import org.jnjeaaaat.openmarket.order.event.OrderCreatedEvent
import org.jnjeaaaat.openmarket.order.repository.OrderItemRepository
import org.jnjeaaaat.openmarket.order.repository.OrderRepository
import org.jnjeaaaat.openmarket.util.logger
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class LockOrderedProduct(
    private val orderRepository: OrderRepository,
    private val cartItemRepository: CartItemRepository,
    private val memberRepository: MemberRepository,
    private val eventPublisher: ApplicationEventPublisher,
    private val orderItemRepository: OrderItemRepository
) {

    private val log = logger()

    @DistributedMultiLock(keys = ["#productLockKeys"])
    operator fun invoke(
        command: CreateOrderCommand,
        memberId: Long,
        productLockKeys: List<String>
    ): CreateOrderResult {

        val buyer = memberRepository.findByIdOrNull(memberId)
            ?: throw MemberException(ErrorCode.NOT_FOUND_MEMBER)

        val cartItems = cartItemRepository.findAllById(command.cartItemIds)

        validateOrderableCartItems(cartItems)
        validateCartItemsMatchCount(cartItems, command.cartItemIds)

        cartItems.forEach {
            it.product.reserveStock(it.quantity)
        }

        val order = orderRepository.save(
            command.toEntity(cartItems, buyer)
        )

        orderItemRepository.saveAll(
            cartItems.map { cartItem ->
                orderItemRepository.save(
                    cartItem.toOrderItem(order)
                )
            }
        )

        eventPublisher.publishEvent(
            OrderCreatedEvent(
                orderId = requireNotNull(order.id),
                memberId = memberId,
                paymentExpiredAt = LocalDateTime.now().plusMinutes(10L)
            )
        )

        return order.toResult()

    }
}