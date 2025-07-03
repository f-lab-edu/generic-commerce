package com.nilgil.commerce.seller

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class SellerBusinessInfo(
    val businessName: String,
    @Column(unique = true)
    val registrationNumber: String,
    val registrationCertificate: String,
    val businessType: String,
    val businessCategory: String,
    val businessAddress: String,
)
