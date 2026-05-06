package org.jnjeaaaat.openmarket.category.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.entity.BaseEntity

@Entity
class Category(

    @Column(nullable = false)
    var name: String,

    val parentId: Int? = null

) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null

}