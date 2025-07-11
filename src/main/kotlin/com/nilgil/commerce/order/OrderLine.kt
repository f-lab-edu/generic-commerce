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
    val price: Int,
    @ManyToOne(fetch = FetchType.LAZY)
    val order: Order,
) : BaseEntity() {
    fun getTotalPrice(): Int = price * quantity
}
