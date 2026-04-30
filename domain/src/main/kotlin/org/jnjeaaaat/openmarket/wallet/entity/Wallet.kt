package org.jnjeaaaat.openmarket.wallet.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.entity.BaseEntity
import org.jnjeaaaat.openmarket.member.entity.Member

@Entity
class Wallet(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    val owner: Member,

    @Column(nullable = false)
    var balance: Long = 0L

) : BaseEntity()