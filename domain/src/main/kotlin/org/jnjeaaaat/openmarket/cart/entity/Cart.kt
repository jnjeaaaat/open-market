package org.jnjeaaaat.openmarket.cart.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.member.entity.Member

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(name = "uk_cart_member", columnNames = ["member_id"])
    ]
)
class Cart(

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", unique = true)
    val member: Member,

    @OneToMany(mappedBy = "cart", cascade = [CascadeType.ALL], orphanRemoval = true)
    val items: MutableList<CartItem> = mutableListOf()

) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    companion object {
        fun of(member: Member) = Cart(member = member)
    }
}