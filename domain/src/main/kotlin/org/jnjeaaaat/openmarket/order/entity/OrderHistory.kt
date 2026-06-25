package org.jnjeaaaat.openmarket.order.entity

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import org.jnjeaaaat.openmarket.order.type.OrderStatus

@Entity
class OrderHistory(

    @ManyToOne(fetch = FetchType.LAZY)
    val orderItem: OrderItem,

    @Enumerated(EnumType.STRING)
    val fromStatus: OrderStatus,

    @Enumerated(EnumType.STRING)
    val toStatus: OrderStatus,

    val reason: String?

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}