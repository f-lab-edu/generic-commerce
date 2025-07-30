package com.nilgil.commerce.order

import com.nilgil.commerce.common.BaseEntity
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class OrderLine(
    @Embedded
    val item: OrderItem,
    val quantity: Int,
) : BaseEntity() {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    lateinit var order: Order

    val totalPrice: Int
        get() = item.price * quantity

    val productItemId: Long
        get() = item.productItemId

    companion object {
        const val MIN_QUANTITY = 1
        const val MAX_QUANTITY = 100
    }

    init {
        require(quantity in MIN_QUANTITY..MAX_QUANTITY) { "주문 수량은 $MIN_QUANTITY ~ $MAX_QUANTITY 사이여야 합니다." }
    }

    internal fun linkOrder(order: Order) {
        this.order = order
    }
}
