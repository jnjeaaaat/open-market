package org.jnjeaaaat.openmarket.category.controller

import jakarta.validation.Valid
import org.jnjeaaaat.openmarket.category.dto.request.AddCategoryRequest
import org.jnjeaaaat.openmarket.category.dto.request.toCommand
import org.jnjeaaaat.openmarket.category.dto.response.AddCategoryResponse
import org.jnjeaaaat.openmarket.category.dto.response.GetCategoryResponse
import org.jnjeaaaat.openmarket.category.dto.response.toResponse
import org.jnjeaaaat.openmarket.category.usecase.AddCategoryUseCase
import org.jnjeaaaat.openmarket.category.usecase.GetCategoryUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/categories")
class CategoryController(
    private val addCategoryUseCase: AddCategoryUseCase,
    private val getCategoryUseCase: GetCategoryUseCase
) {

    @PostMapping
    fun addCategory(
        @RequestBody @Valid request: AddCategoryRequest
    ): ResponseEntity<AddCategoryResponse> {
        return ResponseEntity.ok().body(
            addCategoryUseCase(
                request.toCommand()
            ).toResponse()
        )
    }

    @GetMapping("/{categoryId}")
    fun getCategory(
        @PathVariable categoryId: Long
    ): ResponseEntity<GetCategoryResponse> {
        return ResponseEntity.ok().body(
            getCategoryUseCase(categoryId)
                .toResponse()
        )
    }
}