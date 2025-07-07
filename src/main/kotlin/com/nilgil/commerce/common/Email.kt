package com.nilgil.commerce.common

import jakarta.persistence.Embeddable

@Embeddable
data class Email(
    val value: String,
) {
    companion object {
        private val EMAIL_REGEX = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")
    }

    init {
        require(value.matches(EMAIL_REGEX)) { "Invalid email format: $value" }
    }
}
