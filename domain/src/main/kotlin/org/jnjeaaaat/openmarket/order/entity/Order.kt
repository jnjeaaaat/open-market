package org.jnjeaaaat.openmarket.order.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.member.entity.Member
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
class Order(

    @ManyToOne(fetch = FetchType.LAZY)
    val buyer: Member,

    @Column(nullable = false)
    val totalAmount: Long,

    @CreatedDate
    val orderDate: LocalDateTime
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

}