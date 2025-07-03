package com.nilgil.commerce.seller

import com.nilgil.commerce.common.Email
import com.nilgil.commerce.common.PhoneNumber
import jakarta.persistence.AttributeOverride
import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class SellerContactInfo(
    @AttributeOverride(name = "value", column = Column(name = "email"))
    val email: Email,
    @AttributeOverride(name = "value", column = Column(name = "phone_number"))
    val phoneNumber: PhoneNumber,
)
