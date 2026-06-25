package org.jnjeaaaat.openmarket.order.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.ErrorCode
import org.jnjeaaaat.openmarket.member.entity.Member
import org.jnjeaaaat.openmarket.order.exception.OrderException
import org.jnjeaaaat.openmarket.order.type.OrderItemStatus
import org.jnjeaaaat.openmarket.order.type.OrderStatus
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener::class)
class Order(

    @ManyToOne(fetch = FetchType.LAZY)
    val buyer: Member,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    val orderItems: MutableList<OrderItem> = mutableListOf(),

    @Column(nullable = false)
    val totalAmount: Long,

    @Column(nullable = false)
    val receiverName: String,

    @Column(nullable = false)
    val address: String,

    @Column(nullable = false)
    val zipCode: String,

    @Column(nullable = false)
    val deliveryMessage: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: OrderStatus = OrderStatus.PENDING_PAYMENT,

    @Column(nullable = false)
    var paymentExpiredAt: LocalDateTime
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @CreatedDate
    @Column(updatable = false)
    lateinit var orderDate: LocalDateTime

    fun cancelByPaymentExpiration() {
        if (status != OrderStatus.PENDING_PAYMENT) {
            throw OrderException(ErrorCode.NOT_PENDING_ORDER)
        }

        status = OrderStatus.CANCELED
        orderItems.forEach { it.status = OrderItemStatus.CANCELED }
    }
}