package org.jnjeaaaat.openmarket.cart.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.entity.BaseEntity
import org.jnjeaaaat.openmarket.product.entity.Product

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(name = "uk_cart_item_cart_product", columnNames = ["cart_id", "product_id"])
    ]
)
class CartItem(

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    val cart: Cart,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    val product: Product,

    @Column(nullable = false)
    var quantity: Int,

    @Column(nullable = false)
    var isSelected: Boolean = true

) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun increaseQuantity(quantity: Int) {
        this.quantity += quantity
    }
}