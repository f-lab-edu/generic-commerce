package com.nilgil.commerce.product

import com.nilgil.commerce.common.BaseEntity
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class ProductItemOptionElement(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_item_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val item: ProductItem,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_option_element_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val optionElement: ProductOptionElement,
) : BaseEntity()
