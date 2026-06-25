package org.jnjeaaaat.openmarket.wallet.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class ChargeRequest(
    @NotNull @Min(1)
    val amount: Long
)

data class ChargeResponse(
    val amount: Long,
    val beforeBalance: Long,
    val afterBalance: Long
)