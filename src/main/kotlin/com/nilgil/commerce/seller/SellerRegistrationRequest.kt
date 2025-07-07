package com.nilgil.commerce.seller

import com.nilgil.commerce.common.BaseEntity
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class SellerRegistrationRequest(
    @Embedded
    val businessInfo: SellerBusinessInfo,
    @Embedded
    val contactInfo: SellerContactInfo,
    @Embedded
    val bankAccount: SellerBankAccount,
) : BaseEntity() {
    @Enumerated(EnumType.STRING)
    var status: SellerRegistrationStatus = SellerRegistrationStatus.PENDING

    fun approve() {
        this.status = SellerRegistrationStatus.APPROVED
    }

    fun reject() {
        this.status = SellerRegistrationStatus.REJECTED
    }

    fun cancel() {
        this.status = SellerRegistrationStatus.CANCELLED
    }
}
