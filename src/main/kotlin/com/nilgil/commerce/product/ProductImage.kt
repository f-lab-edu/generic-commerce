package com.nilgil.commerce.product

import com.nilgil.commerce.common.BaseEntity
import com.nilgil.commerce.common.Positionable
import jakarta.persistence.ConstraintMode
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.ForeignKey
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class ProductImage(
    val url: String,
    @Enumerated(EnumType.STRING)
    val type: ProductImageType,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT))
    val product: Product,
    override var position: Int,
) : BaseEntity(),
    Positionable<ProductImage> {
    fun isThumbnailImage() = type == ProductImageType.THUMBNAIL

    fun isGalleryImage() = type == ProductImageType.GALLERY

    fun isContentImage() = type == ProductImageType.CONTENT
}
