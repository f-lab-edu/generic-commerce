package com.nilgil.commerce.product

import com.nilgil.commerce.common.BaseEntity
import com.nilgil.commerce.common.Positionable
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class ProductOption(
    var name: String,
    @ManyToOne(fetch = FetchType.LAZY)
    val product: Product,
    override var position: Int,
) : BaseEntity(),
    Positionable<ProductOption>
