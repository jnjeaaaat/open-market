package org.jnjeaaaat.openmarket.payment.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.member.entity.Member
import org.jnjeaaaat.openmarket.order.entity.Order
import org.jnjeaaaat.openmarket.payment.type.PaymentStatus

@Entity
class Payment(
    @OneToOne(fetch = FetchType.LAZY)
    val order: Order,

    @ManyToOne(fetch = FetchType.LAZY)
    val buyer: Member,

    @Column(nullable = false)
    val amount: Long,

    @Enumerated(EnumType.STRING)
    var status: PaymentStatus,

    @Column(nullable = false)
    val paymentMethod: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
}