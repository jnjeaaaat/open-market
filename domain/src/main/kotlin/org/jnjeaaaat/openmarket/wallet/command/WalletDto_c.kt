package org.jnjeaaaat.openmarket.wallet.command

data class ChargeCommand(
    val amount: Long
)

data class ChargeResult(
    val amount: Long,
    val beforeBalance: Long,
    val afterBalance: Long
)