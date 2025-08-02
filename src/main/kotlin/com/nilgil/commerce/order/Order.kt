package com.nilgil.commerce.order

import com.nilgil.commerce.common.BaseEntity
import com.nilgil.commerce.common.error.CoreException
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "orders")
class Order(
    @Column(unique = true)
    val code: String,
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = [CascadeType.ALL], orphanRemoval = true)
    val lines: List<OrderLine> = listOf(),
    val userId: Long,
) : BaseEntity() {
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.CREATED

    var paymentId: Long? = null

    val totalAmount: Int = lines.sumOf { it.totalPrice }

    init {
        require(lines.isNotEmpty()) {
            "주문 항목은 비어있을 수 없습니다."
        }

        require(lines.size == lines.distinctBy { it.productItemId }.size) {
            "주문 항목에 중복된 상품이 존재할 수 없습니다."
        }

        require(totalAmount >= 0) {
            "총 금액은 0 이상이어야 합니다."
        }

        lines.forEach { it.linkOrder(this) }
    }

    fun pay(paymentId: Long) {
        if (this.status != OrderStatus.CREATED) {
            throw CoreException(
                type = OrderError.INVALID_STATUS_TRANSITION,
                detail = "결제를 진행할 수 없는 상태입니다.",
            )
        }
        this.paymentId = paymentId
        this.status = OrderStatus.PAID
    }

    fun complete() {
        if (this.status != OrderStatus.PAID) {
            throw CoreException(
                type = OrderError.INVALID_STATUS_TRANSITION,
                detail = "완료 처리할 수 없는 상태입니다.",
            )
        }
        this.status = OrderStatus.COMPLETED
    }

    fun cancel() {
        if (this.status != OrderStatus.CREATED && this.status != OrderStatus.PAID) {
            throw CoreException(
                type = OrderError.INVALID_STATUS_TRANSITION,
                detail = "취소할 수 없는 상태입니다.",
            )
        }
        this.status = OrderStatus.CANCELLED
    }

    fun returnOrder() {
        if (this.status != OrderStatus.COMPLETED) {
            throw CoreException(
                type = OrderError.INVALID_STATUS_TRANSITION,
                detail = "반품할 수 없는 상태입니다.",
            )
        }
        this.status = OrderStatus.RETURNED
    }
}
