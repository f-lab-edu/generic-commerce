package com.nilgil.commerce.common

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class PhoneNumber(
    @Column(name = "phone_number")
    val value: String,
) {
    companion object {
        private val PHONE_NUMBER_REGEX = Regex("^01[016789]-\\d{3,4}-\\d{4}$")
    }

    init {
        require(value.matches(PHONE_NUMBER_REGEX)) { "Invalid phone number format: $value" }
    }
}
