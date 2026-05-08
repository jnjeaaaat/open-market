package org.jnjeaaaat.openmarket.category.controller

import org.jnjeaaaat.openmarket.category.dto.request.AddCategoryRequest
import org.jnjeaaaat.openmarket.category.dto.request.toCommand
import org.jnjeaaaat.openmarket.category.usecase.AddCategoryUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/categories")
class CategoryController(
    private val addCategoryUseCase: AddCategoryUseCase
) {

    @PostMapping
    fun addCategory(@RequestBody request: AddCategoryRequest): ResponseEntity<Int> {
        return ResponseEntity.ok(addCategoryUseCase(request.toCommand()))
    }
}