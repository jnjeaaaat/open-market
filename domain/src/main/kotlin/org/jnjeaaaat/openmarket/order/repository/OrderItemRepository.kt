package org.jnjeaaaat.openmarket.order.repository

import org.jnjeaaaat.openmarket.order.entity.OrderItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderItemRepository : JpaRepository<OrderItem, Long>