package org.jnjeaaaat.openmarket.wallet.repository

import org.jnjeaaaat.openmarket.wallet.entity.Wallet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface WalletRepository : JpaRepository<Wallet, Long> {

    @Query(
        """
        select w
        from Wallet w
        where w.member.id = :memberId
        """
    )
    fun findByMemberIdForUpdate(memberId: Long): Wallet?
}