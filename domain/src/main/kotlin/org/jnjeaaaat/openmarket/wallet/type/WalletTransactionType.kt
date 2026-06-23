package org.jnjeaaaat.openmarket.wallet.type

enum class WalletTransactionType(description: String) {
    CHARGE("캐시 충전"),
    PURCHASE("상품 구매"),
    PURCHASE_CANCEL("상품 구매 취소"),
    REFUND("환불"),
    SETTLEMENT("판매자 정산"),
    WITHDRAW("출금")
}