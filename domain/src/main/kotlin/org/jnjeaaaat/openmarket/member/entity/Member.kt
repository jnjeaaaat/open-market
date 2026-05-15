package org.jnjeaaaat.openmarket.member.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.entity.BaseEntity
import org.jnjeaaaat.openmarket.member.type.MemberType

@Entity
class Member(

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var address: String = "",

    @Column(nullable = false)
    var zipCode: String = "",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var memberType: MemberType = MemberType.BUYER

) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}