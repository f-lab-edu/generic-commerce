package com.nilgil.commerce.product

import com.nilgil.commerce.common.PositionableBaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class ProductOption(
    var name: String,
    @ManyToOne(fetch = FetchType.LAZY)
    val product: Product,
    override var position: Int,
) : PositionableBaseEntity<ProductOption>()
