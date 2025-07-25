package com.nilgil.commerce.product

import com.nilgil.commerce.common.BaseEntity
import com.nilgil.commerce.common.Positionable
import jakarta.persistence.Entity

@Entity
class Product(
    var name: String,
    var description: String?,
    var basePrice: Int,
    var isActive: Boolean,
    val sellerId: Long,
    override val position: Int,
) : BaseEntity(),
    Positionable<Product> {
    init {
        validateBasePrice(basePrice)
    }

    private fun validateBasePrice(basePrice: Int) {
        require(basePrice >= 0) { "기본 가격은 0 이상이어야 합니다." }
    }

    fun changeName(name: String) {
        this.name = name
    }

    fun changeDescription(description: String?) {
        this.description = description
    }

    fun changeBasePrice(basePrice: Int) {
        validateBasePrice(basePrice)
        this.basePrice = basePrice
    }

    fun activate() {
        this.isActive = true
    }

    fun deactivate() {
        this.isActive = false
    }
}
