package com.nilgil.commerce.seller

import com.nilgil.commerce.common.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class SellerRegistrationRequest(
    val businessInfo: SellerBusinessInfo,
    val contactInfo: SellerContactInfo,
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
