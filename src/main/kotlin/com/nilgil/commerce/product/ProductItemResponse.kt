package com.nilgil.commerce.product

data class ProductItemResponse(
    val id: Long,
    val productName: String,
    val options: Map<String, String>,
    val thumbnailImageUrl: String?,
    val price: Int,
    val stock: Int,
) {
    companion object {
        fun from(
            productItem: ProductItem,
            options: Map<String, String>,
            thumbnailUrl: String?,
        ): ProductItemResponse =
            ProductItemResponse(
                id = productItem.id,
                productName = productItem.product.name,
                options = options,
                thumbnailImageUrl = thumbnailUrl,
                price = productItem.getAdjustedPrice(),
                stock = productItem.stock,
            )
    }
}
