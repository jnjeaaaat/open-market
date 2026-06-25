package org.jnjeaaaat.openmarket.delivery.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.delivery.type.DeliveryStatus
import org.jnjeaaaat.openmarket.entity.BaseEntity
import org.jnjeaaaat.openmarket.order.entity.OrderItem

@Entity
class Delivery(

    @OneToOne(fetch = FetchType.LAZY)
    val orderItem: OrderItem,

    @Column(nullable = false)
    val carrierName: String,

    @Column(nullable = false)
    val trackingNumber: String,

    @Enumerated(EnumType.STRING)
    val status: DeliveryStatus,

) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}