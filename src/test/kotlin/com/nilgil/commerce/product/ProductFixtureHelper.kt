package com.nilgil.commerce.product

import com.appmattus.kotlinfixture.Fixture
import com.nilgil.commerce.helper.forceSet

fun Fixture.createProduct(
    id: Long? = this(1L..2L),
    name: String = this(),
    description: String? = this(),
    basePrice: Int = this(0..10000),
    active: Boolean = this(),
    sellerId: Long = this(1L..2L),
    position: Int = this(1..100),
): Product =
    Product(
        name = name,
        description = description,
        basePrice = basePrice,
        active = active,
        sellerId = sellerId,
        position = position,
    ).apply {
        id?.let { forceSet("id", it) }
    }

fun Fixture.createProductImage(
    id: Long? = this(1L..2L),
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
    ).apply {
        id?.let { forceSet("id", it) }
    }

internal fun Fixture.createProductItem(
    id: Long? = this(1L..2L),
    priceAdjustment: Int = this(0..10000),
    stock: Int = this(0..100),
    active: Boolean = this(),
    product: Product = this.createProduct(),
    position: Int = this(1..100),
): ProductItem =
    ProductItem(
        priceAdjustment = priceAdjustment,
        stock = stock,
        active = active,
        product = product,
        position = position,
    ).apply {
        id?.let { forceSet("id", it) }
    }

internal fun Fixture.createProductOption(
    id: Long? = this(1L..2L),
    name: String = this(),
    product: Product = this.createProduct(),
    position: Int = this(1..100),
): ProductOption =
    ProductOption(
        name = name,
        product = product,
        position = position,
    ).apply {
        id?.let { forceSet("id", it) }
    }

internal fun Fixture.createProductOptionElement(
    id: Long? = this(1L..2L),
    name: String = this(),
    option: ProductOption = this.createProductOption(),
): ProductOptionElement =
    ProductOptionElement(
        name = name,
        option = option,
    ).apply {
        id?.let { forceSet("id", it) }
    }

internal fun Fixture.createProductItemOptionElement(
    id: Long? = this(1L..2L),
    item: ProductItem = this.createProductItem(),
    optionElement: ProductOptionElement = this.createProductOptionElement(),
): ProductItemOptionElement =
    ProductItemOptionElement(
        item = item,
        optionElement = optionElement,
    ).apply {
        id?.let { forceSet("id", it) }
    }
