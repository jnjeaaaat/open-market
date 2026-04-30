package org.jnjeaaaat.openmarket.cart.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.entity.BaseEntity
import org.jnjeaaaat.openmarket.product.entity.Product

@Entity
class CartItem(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

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

) : BaseEntity()