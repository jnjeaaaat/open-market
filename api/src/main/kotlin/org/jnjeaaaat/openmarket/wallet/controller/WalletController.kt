package org.jnjeaaaat.openmarket.wallet.controller

import jakarta.validation.Valid
import org.jnjeaaaat.openmarket.wallet.dto.ChargeRequest
import org.jnjeaaaat.openmarket.wallet.dto.ChargeResponse
import org.jnjeaaaat.openmarket.wallet.dto.toCommand
import org.jnjeaaaat.openmarket.wallet.dto.toResponse
import org.jnjeaaaat.openmarket.wallet.usecase.WalletChargeUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/wallets")
class WalletController(
    private val walletChargeUseCase: WalletChargeUseCase
) {

    @PostMapping("/members/{memberId}/charge")
    fun charge(
        @RequestBody @Valid chargeRequest: ChargeRequest,
        @PathVariable memberId: Long
    ): ResponseEntity<ChargeResponse> {
        return ResponseEntity.ok(
            walletChargeUseCase(
                chargeRequest.toCommand(),
                memberId
            ).toResponse()
        )
    }

}