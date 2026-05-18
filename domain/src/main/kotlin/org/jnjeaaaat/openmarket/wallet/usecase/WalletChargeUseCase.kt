package org.jnjeaaaat.openmarket.wallet.usecase

import org.jnjeaaaat.openmarket.ErrorCode.NOT_FOUND_WALLET
import org.jnjeaaaat.openmarket.lock.annotation.DistributedLock
import org.jnjeaaaat.openmarket.util.logger
import org.jnjeaaaat.openmarket.wallet.command.ChargeCommand
import org.jnjeaaaat.openmarket.wallet.command.ChargeResult
import org.jnjeaaaat.openmarket.wallet.command.toChargeResult
import org.jnjeaaaat.openmarket.wallet.entity.WalletTransaction
import org.jnjeaaaat.openmarket.wallet.exception.WalletException
import org.jnjeaaaat.openmarket.wallet.repository.WalletRepository
import org.jnjeaaaat.openmarket.wallet.repository.WalletTransactionRepository
import org.jnjeaaaat.openmarket.wallet.usecase.component.WalletChargeLimitValidator
import org.springframework.stereotype.Service

@Service
class WalletChargeUseCase(
    private val walletRepository: WalletRepository,
    private val walletTransactionRepository: WalletTransactionRepository,
    private val validateChargeLimit: WalletChargeLimitValidator,
) {

    private val log = logger()

    @DistributedLock(key = "'wallet:' + #memberId")
    operator fun invoke(command: ChargeCommand, memberId: Long): ChargeResult {

        validateChargeLimit(command.amount, memberId)

        val wallet = walletRepository.findByMemberIdForUpdate(memberId)
            ?: throw WalletException(NOT_FOUND_WALLET)

        val beforeBalance = wallet.charge(command.amount)

        walletTransactionRepository.save(
            WalletTransaction.createCharge(
                wallet = wallet,
                amount = command.amount,
                beforeBalance = beforeBalance,
                afterBalance = wallet.balance
            )
        )

        // TODO: event 발행

        log.info { "key = wallet:$memberId, afterBalance = ${wallet.balance} " }

        return wallet.toChargeResult(
            command.amount
        )
    }
}