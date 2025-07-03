package com.nilgil.commerce.common

import jakarta.persistence.Embeddable

@Embeddable
data class PhoneNumber(
    val value: String,
) {
    init {
        require(value.matches(Regex("^01[016789]-\\d{3,4}-\\d{4}$"))) { "Invalid phone number format" }
    }
}
