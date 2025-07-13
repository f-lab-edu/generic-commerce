package com.nilgil.commerce.order

import com.nilgil.commerce.common.BaseEntity
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne

@Entity
class OrderLine(
    @Embedded
    val item: OrderItem,
    val quantity: Int,
    @ManyToOne(fetch = FetchType.LAZY)
    val order: Order,
) : BaseEntity() {
    init {
        require(quantity >= 1) { "수량은 1개 이상이어야 합니다." }
    }

    fun getTotalPrice(): Int = item.price * quantity
}
