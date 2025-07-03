package com.nilgil.commerce.seller

import jakarta.persistence.Embeddable

@Embeddable
data class SellerBankAccount(
    val bankName: String,
    val accountNumber: String,
    val accountHolder: String,
)
