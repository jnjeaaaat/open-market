package org.jnjeaaaat.openmarket.order.usecase

import org.jnjeaaaat.openmarket.ErrorCode.NOT_FOUND_CART
import org.jnjeaaaat.openmarket.cart.exception.CartException
import org.jnjeaaaat.openmarket.cart.repository.CartItemRepository
import org.jnjeaaaat.openmarket.cart.repository.CartRepository
import org.jnjeaaaat.openmarket.cart.usecase.component.CartItemValidator.validateCartItemIdsNotEmpty
import org.jnjeaaaat.openmarket.cart.usecase.component.CartItemValidator.validateCartItemsMatchCount
import org.jnjeaaaat.openmarket.cart.usecase.component.CartItemValidator.validateOrderableCartItems
import org.jnjeaaaat.openmarket.order.command.CreateOrderCommand
import org.jnjeaaaat.openmarket.order.command.CreateOrderResult
import org.springframework.stereotype.Service

@Service
class CreateOrderUseCase(
    private val cartRepository: CartRepository,
    private val cartItemRepository: CartItemRepository,
    private val lockOrderedProduct: LockOrderedProduct,
) {

    companion object {
        private const val PRODUCT_STOCK_KEY = "product:stock:"
    }

    operator fun invoke(
        command: CreateOrderCommand,
        memberId: Long
    ): CreateOrderResult {

        validateCartItemIdsNotEmpty(command.cartItemIds)

        val cart = cartRepository.findByMemberId(memberId)
            ?: throw CartException(NOT_FOUND_CART)

        val cartItems = cartItemRepository.findAllByIdInAndCart(
            command.cartItemIds,
            cart
        )

        validateOrderableCartItems(cartItems)
        validateCartItemsMatchCount(cartItems, command.cartItemIds)

        val productLockKeys = cartItems
            .map { requireNotNull(it.product.id) }
            .distinct()
            .sorted()
            .map { productId -> "$PRODUCT_STOCK_KEY$productId" }

        return lockOrderedProduct(
            command = command,
            memberId = memberId,
            productLockKeys = productLockKeys
        )
    }

}
