package org.jnjeaaaat.openmarket.cart.usecase

import org.jnjeaaaat.openmarket.ErrorCode.NOT_FOUND_CART
import org.jnjeaaaat.openmarket.ErrorCode.NOT_FOUND_PRODUCT
import org.jnjeaaaat.openmarket.cart.command.AddCartItemCommand
import org.jnjeaaaat.openmarket.cart.command.AddCartItemResult
import org.jnjeaaaat.openmarket.cart.command.toEntity
import org.jnjeaaaat.openmarket.cart.command.toResult
import org.jnjeaaaat.openmarket.cart.entity.Cart
import org.jnjeaaaat.openmarket.cart.entity.CartItem
import org.jnjeaaaat.openmarket.cart.exception.CartException
import org.jnjeaaaat.openmarket.cart.repository.CartItemRepository
import org.jnjeaaaat.openmarket.cart.repository.CartRepository
import org.jnjeaaaat.openmarket.product.entity.Product
import org.jnjeaaaat.openmarket.product.exception.ProductException
import org.jnjeaaaat.openmarket.product.repository.ProductRepository
import org.jnjeaaaat.openmarket.product.service.ProductValidator
import org.jnjeaaaat.openmarket.util.logger
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AddCartItemUseCase(
    private val cartRepository: CartRepository,
    private val cartItemRepository: CartItemRepository,
    private val productRepository: ProductRepository,
    private val productValidator: ProductValidator
) {

    private val log = logger()

    @Transactional
    operator fun invoke(command: AddCartItemCommand, memberId: Long): AddCartItemResult {

        log.info { "cart 조회 query" }
        val cart: Cart = cartRepository.findByMemberId(memberId)
            ?: throw CartException(NOT_FOUND_CART)

        log.info { "product 조회 query" }
        val product: Product = productRepository.findByIdOrNull(command.productId)
            ?.also { productValidator.validateSoldOut(it) }
            ?: throw ProductException(NOT_FOUND_PRODUCT)

        log.info { "cartItem 있는지 조회 query" }
        val cartItem = cartItemRepository
            .findByCartAndProduct(cart, product)

        val savedCartItem = cartItem?.let {
            increaseCartItem(
                cartItem = it,
                stock = product.stock,
                quantity = command.quantity
            )
        } ?: addNewCartItem(
            command = command,
            cart = cart,
            product = product,
            stock = product.stock,
            quantity = command.quantity
        )

        return savedCartItem.toResult()
    }

    /**
     * 기존 cart item 수량 증가
     */
    private fun increaseCartItem(
        cartItem: CartItem,
        stock: Int,
        quantity: Int
    ): CartItem {
        productValidator.validateStock(stock, cartItem.quantity + quantity)

        cartItem.increaseQuantity(quantity)

        return cartItem
    }

    /**
     * 새로운 item 추가
     */

    private fun addNewCartItem(
        command: AddCartItemCommand,
        cart: Cart,
        product: Product,
        stock: Int,
        quantity: Int
    ): CartItem {
        productValidator.validateStock(stock, quantity)

        val savedCartItem = cartItemRepository.save(
            command.toEntity(cart, product)
        )

        cart.addItem(savedCartItem)

        return savedCartItem
    }
}
