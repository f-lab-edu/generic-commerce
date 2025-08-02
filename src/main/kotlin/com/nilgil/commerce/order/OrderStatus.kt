package com.nilgil.commerce.order

enum class OrderStatus {
    CREATED,
    PAID,
    COMPLETED,
    CANCELLED,
    RETURNED,
    ;

    companion object {
        val activeStatuses = listOf(CREATED, PAID)

        val terminalStatuses = listOf(COMPLETED, CANCELLED, RETURNED)
    }

    fun isActive() = activeStatuses.contains(this)

    fun isTerminal() = terminalStatuses.contains(this)
}
