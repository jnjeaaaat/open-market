package org.jnjeaaaat.openmarket.category.entity

import jakarta.persistence.*
import org.jnjeaaaat.openmarket.entity.BaseEntity

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(
            name = "uk_category_parent_name",
            columnNames = ["parent_id", "name"]
        )
    ],
    indexes = [
        Index(
            name = "idx_category_parent_id",
            columnList = "parent_id"
        ),
        Index(
            name = "idx_category_sort_order",
            columnList = "sort_order"
        )
    ]
)
class Category(

    @Column(nullable = false)
    var name: String,

    @Column(name = "parent_id", nullable = true)
    var parentId: Long? = null,

    @Column(nullable = false)
    var depth: Int? = null,

    @Column(nullable = false)
    var sortOrder: Int? = null,

    @Column(nullable = false)
    var isVisible: Boolean = true

) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}