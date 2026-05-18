package org.jnjeaaaat.openmarket.wallet.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.member.entity.Member

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(name = "uk_wallet_member", columnNames = ["member_id"])
    ]
)
class Wallet(

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", unique = true)
    val member: Member,

    @Column(nullable = false)
    var balance: Long = 0L

) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Version
    var version: Long = 0

    fun charge(amount: Long): Long {
        val before = balance
        balance += amount
        return before
    }

    companion object {
        fun of(member: Member) = Wallet(member = member)
    }
}