package org.jnjeaaaat.openmarket.order.controller

import jakarta.validation.Valid
import org.jnjeaaaat.openmarket.order.dto.CreateOrderRequest
import org.jnjeaaaat.openmarket.order.dto.CreateOrderResponse
import org.jnjeaaaat.openmarket.order.dto.toCommand
import org.jnjeaaaat.openmarket.order.dto.toResponse
import org.jnjeaaaat.openmarket.order.usecase.CreateOrderUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController(
    private val createOrderUseCase: CreateOrderUseCase
) {

    @PostMapping("/members/{memberId}")
    fun createOrder(
        @RequestBody @Valid request: CreateOrderRequest,
        @PathVariable memberId: Long
    ): ResponseEntity<CreateOrderResponse> {
        return ResponseEntity.ok(
            createOrderUseCase(
                request.toCommand(),
                memberId
            ).toResponse()
        )
    }

}