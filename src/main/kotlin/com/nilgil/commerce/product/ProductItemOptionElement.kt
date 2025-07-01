package com.nilgil.commerce.product

import com.nilgil.commerce.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class ProductItemOptionElement(
    @ManyToOne(fetch = FetchType.LAZY)
    val item: ProductItem,
    @ManyToOne(fetch = FetchType.LAZY)
    val optionElement: ProductOptionElement,
) : BaseEntity()
