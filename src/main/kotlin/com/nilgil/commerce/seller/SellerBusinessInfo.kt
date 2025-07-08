package com.nilgil.commerce.seller

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class SellerBusinessInfo(
    @Column(name = "business_name")
    val name: String,
    @Column(name = "business_type")
    val type: String,
    @Column(name = "business_category")
    val category: String,
    @Column(name = "business_address")
    val address: String,
    @Column(name = "business_registration_number")
    val registrationNumber: String,
    @Column(name = "business_registration_certificate")
    val registrationCertificate: String,
)
