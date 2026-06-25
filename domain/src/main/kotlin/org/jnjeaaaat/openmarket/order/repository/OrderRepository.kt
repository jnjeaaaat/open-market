package org.jnjeaaaat.openmarket.order.repository

import org.jnjeaaaat.openmarket.order.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long>