package com.nilgil.commerce.order

import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {
    fun findByUserIdAndStatusIn(
        userId: Long,
        statuses: List<OrderStatus>,
    ): Order?
}
