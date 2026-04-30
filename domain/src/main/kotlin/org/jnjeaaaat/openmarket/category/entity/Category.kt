package org.jnjeaaaat.openmarket.category.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.entity.BaseEntity

@Entity
class Category(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false)
    var name: String,

    val parentId: Int? = null

) : BaseEntity()