package org.jnjeaaaat.openmarket.cart.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.member.entity.Member
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@EntityListeners(AuditingEntityListener::class)
class Cart(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,

    @OneToMany(mappedBy = "cart", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: MutableList<CartItem> = mutableListOf()

)