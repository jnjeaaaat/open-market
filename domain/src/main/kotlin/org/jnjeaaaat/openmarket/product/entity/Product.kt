package org.jnjeaaaat.openmarket.product.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.category.entity.Category
import org.jnjeaaaat.openmarket.entity.BaseEntity
import org.jnjeaaaat.openmarket.member.entity.Member
import org.jnjeaaaat.openmarket.product.type.ProductStatus

@Entity
class Product(

    @ManyToOne(fetch = FetchType.LAZY)
    val seller: Member,

    @ManyToOne(fetch = FetchType.LAZY)
    var category: Category,

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var price: Long,

    @Column(nullable = false)
    var discountRate: Int = 0,

    @Column(nullable = false)
    var stock: Int,

    @Column(nullable = false)
    var reservedStock: Int = 0,

    @Enumerated(EnumType.STRING)
    var status: ProductStatus = ProductStatus.NORMAL

) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun reserveStock(quantity: Int) {
        reservedStock += quantity
    }

    fun releaseReservedStock(quantity: Int) {
        reservedStock -= quantity
    }
}