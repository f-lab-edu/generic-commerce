package com.nilgil.commerce.product

import com.nilgil.commerce.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class Product(
    var name: String,
    var description: String?,
    var basePrice: Int,
    var categoryId: Long,
    val sellerId: Long,
) : BaseEntity() {
    @Enumerated(EnumType.STRING)
    var status: ProductStatus = ProductStatus.HIDDEN
}
