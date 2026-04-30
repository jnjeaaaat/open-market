package org.jnjeaaaat.openmarket.orderItem.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.member.entity.Member
import org.jnjeaaaat.openmarket.order.entity.Order
import org.jnjeaaaat.openmarket.orderItem.type.OrderStatus
import org.jnjeaaaat.openmarket.product.entity.Product
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class OrderItem(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val order: Order,

    @OneToOne(fetch = FetchType.LAZY)
    val product: Product,

    @ManyToOne(fetch = FetchType.LAZY)
    val seller: Member,

    @Column(nullable = false)
    val orderPrice: Long,

    @Column(nullable = false)
    val quantity: Int,

    @Enumerated(EnumType.STRING)
    var status: OrderStatus,

    @CreatedDate
    val orderDate: LocalDateTime
)