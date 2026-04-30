package org.jnjeaaaat.openmarket.delivery.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.delivery.type.DeliveryStatus
import org.jnjeaaaat.openmarket.entity.BaseEntity
import org.jnjeaaaat.openmarket.orderItem.entity.OrderItem

@Entity
class Delivery(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    val orderItem: OrderItem,

    @Column(nullable = false)
    val carrierName: String,

    @Column(nullable = false)
    val trackingNumber: String,

    @Enumerated(EnumType.STRING)
    val status: DeliveryStatus,

    @Column(nullable = false)
    val receiverName: String,

    @Column(nullable = false)
    val address: String,

    @Column(nullable = false)
    val zipCode: String

) : BaseEntity()