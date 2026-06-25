package org.jnjeaaaat.openmarket.cart.controller

import org.jnjeaaaat.openmarket.cart.dto.AddCartItemRequest
import org.jnjeaaaat.openmarket.cart.dto.AddCartItemResponse
import org.jnjeaaaat.openmarket.cart.dto.toCommand
import org.jnjeaaaat.openmarket.cart.dto.toResponse
import org.jnjeaaaat.openmarket.cart.usecase.AddCartItemUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/carts")
class CartController(
    private val addCartItemUseCase: AddCartItemUseCase
) {

    @PostMapping("/items/members/{memberId}")
    fun addCartItem(
        @PathVariable memberId: Long,
        @RequestBody request: AddCartItemRequest
    ): ResponseEntity<AddCartItemResponse> {
        return ResponseEntity.ok(
            addCartItemUseCase(
                request.toCommand(),
                memberId
            ).toResponse()
        )
    }
}