package com.nilgil.commerce.product

import com.nilgil.commerce.common.PositionableBaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class ProductItem(
    var name: String,
    var priceAdjustment: Int = 0,
    var stock: Int = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    val product: Product,
    override var position: Int,
) : PositionableBaseEntity<ProductItem>() {
    @Enumerated(EnumType.STRING)
    var status: ProductItemStatus = ProductItemStatus.HIDDEN
}
