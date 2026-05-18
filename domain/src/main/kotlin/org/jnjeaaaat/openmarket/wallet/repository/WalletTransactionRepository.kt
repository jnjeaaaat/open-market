package org.jnjeaaaat.openmarket.wallet.repository

import org.jnjeaaaat.openmarket.wallet.entity.WalletTransaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WalletTransactionRepository : JpaRepository<WalletTransaction, Long>