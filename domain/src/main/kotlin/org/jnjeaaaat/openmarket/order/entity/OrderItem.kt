package org.jnjeaaaat.openmarket.order.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.member.entity.Member
import org.jnjeaaaat.openmarket.order.type.OrderItemStatus
import org.jnjeaaaat.openmarket.product.entity.Product
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class OrderItem(

    @ManyToOne(fetch = FetchType.LAZY)
    val order: Order,

    @ManyToOne(fetch = FetchType.LAZY)
    val product: Product,

    @ManyToOne(fetch = FetchType.LAZY)
    val seller: Member,

    @Column(nullable = false)
    val orderPrice: Long,

    @Column(nullable = false)
    val quantity: Int,

    @Enumerated(EnumType.STRING)
    var status: OrderItemStatus = OrderItemStatus.PENDING_PAYMENT,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @CreatedDate
    @Column(updatable = false)
    lateinit var orderDate: LocalDateTime
}