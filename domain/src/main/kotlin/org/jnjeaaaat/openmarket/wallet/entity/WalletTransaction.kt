package org.jnjeaaaat.openmarket.wallet.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.wallet.type.WalletTransactionType

@Entity
class WalletTransaction(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    val wallet: Wallet,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: WalletTransactionType,

    @Column(nullable = false)
    val amount: Long,

    @Column(nullable = false)
    val beforeBalance: Long,

    @Column(nullable = false)
    val afterBalance: Long
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    companion object {
        fun createCharge(
            wallet: Wallet,
            amount: Long,
            beforeBalance: Long,
            afterBalance: Long
        ): WalletTransaction {
            return WalletTransaction(
                wallet = wallet,
                type = WalletTransactionType.CHARGE,
                amount = amount,
                beforeBalance = beforeBalance,
                afterBalance = afterBalance
            )
        }
    }
}