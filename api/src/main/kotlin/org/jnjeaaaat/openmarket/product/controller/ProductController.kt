package org.jnjeaaaat.openmarket.product.controller

import jakarta.validation.Valid
import org.jnjeaaaat.openmarket.product.dto.CreateProductRequest
import org.jnjeaaaat.openmarket.product.dto.CreateProductResponse
import org.jnjeaaaat.openmarket.product.dto.toCommand
import org.jnjeaaaat.openmarket.product.dto.toResponse
import org.jnjeaaaat.openmarket.product.usecase.CreateProductUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/products")
class ProductController(
    private val createProductUseCase: CreateProductUseCase
) {

    @PostMapping("/members/{memberId}")
    fun createProduct(
        @RequestBody @Valid request: CreateProductRequest,
        @PathVariable memberId: Long
    ): ResponseEntity<CreateProductResponse> {
        return ResponseEntity.ok().body(
            createProductUseCase(
                request.toCommand(), memberId
            ).toResponse()
        )
    }

}