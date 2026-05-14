package org.jnjeaaaat.openmarket.product.event

import org.jnjeaaaat.openmarket.common.DomainEvent

sealed interface ProductEvent : DomainEvent

data class ProductCreatedEvent(val productId: Long) : ProductEvent