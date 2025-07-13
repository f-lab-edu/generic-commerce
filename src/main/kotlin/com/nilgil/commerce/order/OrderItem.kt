package com.nilgil.commerce.order

import com.nilgil.commerce.common.StringListConverter
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Embeddable

@Embeddable
data class OrderItem(
    @Column(name = "item_title")
    val title: String,
    @Convert(converter = StringListConverter::class)
    @Column(name = "item_options")
    val options: List<String>,
    @Column(name = "item_price")
    val price: Int,
    @Column(name = "item_thumbnail_image_url")
    val thumbnailImageUrl: String?,
    @Column(name = "product_item_id")
    val productItemId: Long,
)
