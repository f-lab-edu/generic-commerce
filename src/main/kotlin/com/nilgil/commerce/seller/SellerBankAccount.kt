package com.nilgil.commerce.seller

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class SellerBankAccount(
    @Column(name = "bank_name")
    val bankName: String,
    @Column(name = "bank_account_number")
    val accountNumber: String,
    @Column(name = "bank_account_holder")
    val accountHolder: String,
)
