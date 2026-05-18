package org.jnjeaaaat.openmarket.wallet.command

import org.jnjeaaaat.openmarket.wallet.entity.Wallet

fun Wallet.toChargeResult(amount: Long): ChargeResult {
    return ChargeResult(
        amount = amount,
        beforeBalance = this.balance - amount,
        afterBalance = this.balance
    )
}