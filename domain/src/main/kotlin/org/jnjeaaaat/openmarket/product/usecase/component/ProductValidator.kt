package org.jnjeaaaat.openmarket.product.usecase.component

import org.jnjeaaaat.openmarket.ErrorCode.NOT_AVAILABLE_PRODUCT
import org.jnjeaaaat.openmarket.ErrorCode.NOT_ENOUGH_PRODUCT_STOCK
import org.jnjeaaaat.openmarket.product.entity.Product
import org.jnjeaaaat.openmarket.product.exception.ProductException
import org.jnjeaaaat.openmarket.product.type.ProductStatus.SOLD_OUT

object ProductValidator {

    fun validateSoldOut(product: Product) {
        if (product.status == SOLD_OUT) {
            throw ProductException(NOT_AVAILABLE_PRODUCT)
        }
    }

    fun validateStock(stock: Int, requestedQuantity: Int) {
        if (stock < requestedQuantity) {
            throw ProductException(NOT_ENOUGH_PRODUCT_STOCK)
        }
    }
}