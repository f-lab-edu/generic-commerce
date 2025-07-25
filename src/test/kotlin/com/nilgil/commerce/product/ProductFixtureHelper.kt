package com.nilgil.commerce.product

import com.appmattus.kotlinfixture.Fixture

fun Fixture.createProduct(
    name: String = this(),
    description: String? = this(),
    basePrice: Int = this(0..10000),
    isActive: Boolean = this(),
    sellerId: Long = this(1L..2L),
    position: Int = this(1..100),
): Product =
    Product(
        name = name,
        description = description,
        basePrice = basePrice,
        isActive = isActive,
        sellerId = sellerId,
        position = position,
    )

fun Fixture.createProductImage(
    url: String = this(),
    type: ProductImageType = this(),
    product: Product = this.createProduct(),
    position: Int = this(1..100),
): ProductImage =
    ProductImage(
        url = url,
        type = type,
        product = product,
        position = position,
    )

fun Fixture.createProductItem(
    priceAdjustment: Int = this(0..10000),
    stock: Int = this(0..100),
    isActive: Boolean = this(),
    product: Product = this.createProduct(),
    position: Int = this(1..100),
): ProductItem =
    ProductItem(
        priceAdjustment = priceAdjustment,
        stock = stock,
        isActive = isActive,
        product = product,
        position = position,
    )
