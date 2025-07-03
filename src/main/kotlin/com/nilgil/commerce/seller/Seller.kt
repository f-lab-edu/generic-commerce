package com.nilgil.commerce.seller

import com.nilgil.commerce.common.BaseEntity
import com.nilgil.commerce.common.Email
import com.nilgil.commerce.common.PhoneNumber
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Entity
class Seller(
    var businessInfo: SellerBusinessInfo,
    var contactInfo: SellerContactInfo,
    var bankAccount: SellerBankAccount,
    var introduction: String?,
    var brandLogo: String?,
) : BaseEntity() {
    @Enumerated(EnumType.STRING)
    var status: SellerStatus = SellerStatus.ACTIVE

    fun activate() {
        this.status = SellerStatus.ACTIVE
    }

    fun deactivate() {
        this.status = SellerStatus.INACTIVE
    }

    fun block() {
        this.status = SellerStatus.BLOCKED
    }

    fun unblock() {
        this.status = SellerStatus.ACTIVE
    }

    fun changeBusinessInfo(businessInfo: SellerBusinessInfo) {
        this.businessInfo = businessInfo
    }

    fun changeContactInfo(contactInfo: SellerContactInfo) {
        this.contactInfo = contactInfo
    }

    fun changeEmail(email: Email) {
        this.contactInfo = this.contactInfo.copy(email = email)
    }

    fun changePhoneNumber(phoneNumber: PhoneNumber) {
        this.contactInfo = this.contactInfo.copy(phoneNumber = phoneNumber)
    }

    fun changeBankAccount(bankAccount: SellerBankAccount) {
        this.bankAccount = bankAccount
    }

    fun changeIntroduction(introduction: String) {
        this.introduction = introduction
    }

    fun changeBrandLogo(brandLogo: String) {
        this.brandLogo = brandLogo
    }
}
