package com.nilgil.commerce.common

import jakarta.persistence.Embeddable

@Embeddable
data class Email(
    val value: String,
) {
    init {
        require(value.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))) { "Invalid email format" }
    }
}
