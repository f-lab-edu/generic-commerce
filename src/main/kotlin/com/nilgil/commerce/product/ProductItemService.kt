package com.nilgil.commerce.product

import com.nilgil.commerce.common.error.CoreError
import com.nilgil.commerce.common.error.CoreException
import org.springframework.stereotype.Service

@Service
class ProductItemService(
    private val productItemRepository: ProductItemRepository,
    private val productImageRepository: ProductImageRepository,
    private val productItemOptionElementRepository: ProductItemOptionElementRepository,
) {
    fun getProductItem(id: Long): ProductItemResponse {
        val productItem = findProductItemOrThrow(id)
        val options = findOptionMap(productItem)
        val thumbnailUrl = findThumbnailUrlOrNull(productItem.product)

        return ProductItemResponse.from(
            productItem = productItem,
            options = options,
            thumbnailUrl = thumbnailUrl,
        )
    }

    fun getProductItems(ids: List<Long>): List<ProductItemResponse> {
        val productItems = productItemRepository.findWithProductByIdIn(ids)
        if (productItems.isEmpty()) {
            return emptyList()
        }

        val thumbnailUrlMap = getProductIdThumbnailUrlMap(productItems)
        val optionElementsMap = getProductItemIdOptionElementsMap(productItems)

        val responseMap =
            productItems.associateBy(
                keySelector = { it.id },
                valueTransform = { item ->
                    ProductItemResponse.from(
                        productItem = item,
                        options = optionElementsMap[item.id] ?: emptyMap(),
                        thumbnailUrl = thumbnailUrlMap[item.product.id],
                    )
                },
            )

        return ids.mapNotNull { responseMap[it] }
    }

    private fun findProductItemOrThrow(id: Long): ProductItem =
        productItemRepository.findWithProductById(id)
            ?: throw CoreException(
                type = CoreError.NOT_FOUND_ERROR,
                detail = "상품 아이템을 찾을 수 없습니다. id: $id",
            )

    private fun findOptionMap(item: ProductItem): Map<String, String> =
        productItemOptionElementRepository
            .findAllWithOptionDetailsByItem(item)
            .associate { it.optionElement.option.name to it.optionElement.name }

    private fun findThumbnailUrlOrNull(product: Product): String? =
        productImageRepository.findByProductAndType(product, ProductImageType.THUMBNAIL)?.url

    private fun getProductIdThumbnailUrlMap(productItems: List<ProductItem>): Map<Long, String> {
        val products = productItems.map { productItem -> productItem.product }
        return productImageRepository
            .findAllByProductInAndType(products, ProductImageType.THUMBNAIL)
            .associate { it.product.id to it.url }
    }

    private fun getProductItemIdOptionElementsMap(productItems: List<ProductItem>): Map<Long, Map<String, String>> =
        productItemOptionElementRepository
            .findAllWithOptionDetailsByItemIn(productItems)
            .groupBy { it.item.id }
            .mapValues { it.value.associate { it -> it.optionElement.option.name to it.optionElement.name } }
}
