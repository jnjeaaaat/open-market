package org.jnjeaaaat.openmarket.wallet.type

enum class WalletTransactionType(description: String) {
    CHARGE("캐시 충전"),
    SETTLEMENT("주문 정산"),
    REFUND("환불"),
    WITHDRAW("출금")
}