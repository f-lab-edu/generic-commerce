package com.nilgil.commerce.seller

import com.nilgil.commerce.common.Email
import com.nilgil.commerce.common.PhoneNumber
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded

@Embeddable
data class SellerContactInfo(
    @Embedded
    val email: Email,
    @Embedded
    val phoneNumber: PhoneNumber,
)
