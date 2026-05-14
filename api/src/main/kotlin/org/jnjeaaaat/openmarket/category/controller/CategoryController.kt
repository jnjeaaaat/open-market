package org.jnjeaaaat.openmarket.category.controller

import jakarta.validation.Valid
import org.jnjeaaaat.openmarket.category.dto.*
import org.jnjeaaaat.openmarket.category.usecase.AddCategoryUseCase
import org.jnjeaaaat.openmarket.category.usecase.GetCategoryTreeUseCase
import org.jnjeaaaat.openmarket.category.usecase.GetCategoryUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/categories")
class CategoryController(
    private val addCategoryUseCase: AddCategoryUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getCategoryTreeUseCase: GetCategoryTreeUseCase
) {

    @PostMapping
    fun addCategory(
        @RequestBody @Valid request: AddCategoryRequest
    ): ResponseEntity<AddCategoryResponse> {
        return ResponseEntity.ok(
            addCategoryUseCase(
                request.toCommand()
            ).toResponse()
        )
    }

    @GetMapping("/{categoryId}")
    fun getCategory(
        @PathVariable categoryId: Long
    ): ResponseEntity<GetCategoryResponse> {
        return ResponseEntity.ok(
            getCategoryUseCase(categoryId)
                .toResponse()
        )
    }

    @GetMapping("/trees")
    fun getCategoryTree():
            ResponseEntity<List<CategoryTreeResponse>> {
        return ResponseEntity.ok(
            getCategoryTreeUseCase()
                .toResponse()
        )
    }
}