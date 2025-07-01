package com.nilgil.commerce.product

import com.nilgil.commerce.common.BaseEntity
import com.nilgil.commerce.common.Positionable
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class ProductImage(
    val url: String,
    @Enumerated(EnumType.STRING)
    val type: ProductImageType,
    @ManyToOne(fetch = FetchType.LAZY)
    val product: Product,
    override var position: Int,
) : BaseEntity(),
    Positionable<ProductImage>
