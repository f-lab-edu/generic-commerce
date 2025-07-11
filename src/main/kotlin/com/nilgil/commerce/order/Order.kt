package com.nilgil.commerce.order

import com.nilgil.commerce.common.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "orders")
class Order(
    @Column(unique = true)
    val code: String,
    val totalAmount: Int,
    val userId: Long,
) : BaseEntity() {
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.CREATED

    var paymentId: Long? = null

    fun pay(paymentId: Long) {
        if (this.status != OrderStatus.CREATED) {
            throw IllegalStateException("결제를 진행할 수 없는 상태입니다.")
        }
        this.paymentId = paymentId
        this.status = OrderStatus.PAID
    }

    fun complete() {
        if (this.status != OrderStatus.PAID) {
            throw IllegalStateException("완료 처리할 수 없는 상태입니다.")
        }
        this.status = OrderStatus.COMPLETED
    }

    fun cancel() {
        if (this.status != OrderStatus.CREATED && this.status != OrderStatus.PAID) {
            throw IllegalStateException("취소할 수 없는 상태입니다.")
        }
        this.status = OrderStatus.CANCELLED
    }

    fun returnOrder() {
        if (this.status != OrderStatus.COMPLETED) {
            throw IllegalStateException("반품할 수 없는 상태입니다.")
        }
        this.status = OrderStatus.RETURNED
    }
}
