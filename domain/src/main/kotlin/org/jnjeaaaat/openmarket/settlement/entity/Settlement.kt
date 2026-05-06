package org.jnjeaaaat.openmarket.settlement.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.member.entity.Member
import org.jnjeaaaat.openmarket.orderItem.entity.OrderItem
import org.jnjeaaaat.openmarket.settlement.type.SettlementStatus
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class Settlement(

    @OneToOne(fetch = FetchType.LAZY)
    val orderItem: OrderItem,

    @OneToOne(fetch = FetchType.LAZY)
    val seller: Member,

    @Column(nullable = false)
    val settleAmount: Long,

    @Enumerated(EnumType.STRING)
    var status: SettlementStatus,

    @CreatedDate
    val settledAt: LocalDateTime

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}