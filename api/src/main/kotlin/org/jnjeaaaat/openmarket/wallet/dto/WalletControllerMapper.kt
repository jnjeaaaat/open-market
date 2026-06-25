package org.jnjeaaaat.openmarket.wallet.dto

import org.jnjeaaaat.openmarket.wallet.command.ChargeCommand
import org.jnjeaaaat.openmarket.wallet.command.ChargeResult

fun ChargeRequest.toCommand(): ChargeCommand {
    return ChargeCommand(
        amount = amount
    )
}

fun ChargeResult.toResponse(): ChargeResponse {
    return ChargeResponse(
        amount = amount,
        beforeBalance = beforeBalance,
        afterBalance = afterBalance
    )
}