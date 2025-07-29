package com.nilgil.commerce.product

import com.nilgil.commerce.common.BaseEntity
import com.nilgil.commerce.common.Positionable
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class ProductItem(
    var priceAdjustment: Int,
    var stock: Int,
    var active: Boolean,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val product: Product,
    override var position: Int,
) : BaseEntity(),
    Positionable<ProductItem> {
    init {
        validatePriceAdjustment(priceAdjustment)
        require(stock >= 0) { "재고는 0 이상이어야 합니다." }
    }

    private fun validatePriceAdjustment(priceAdjustment: Int) {
        require(product.basePrice + priceAdjustment >= 0) { "조정된 판매 가격은 0 이상이어야 합니다." }
    }

    fun getAdjustedPrice(): Int = product.basePrice + priceAdjustment

    fun changePriceAdjustment(priceAdjustment: Int) {
        validatePriceAdjustment(priceAdjustment)
        this.priceAdjustment = priceAdjustment
    }

    fun increaseStock(amount: Int) {
        this.stock += amount
    }

    fun decreaseStock(amount: Int) {
        require(amount <= this.stock) { "재고가 부족합니다." }
        this.stock -= amount
    }

    fun activate() {
        this.active = true
    }

    fun deactivate() {
        this.active = false
    }
}
