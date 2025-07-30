package com.nilgil.commerce.order

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class OrderItemOptionMapConverter : AttributeConverter<Map<String, String>, String> {
    private val objectMapper = ObjectMapper()

    override fun convertToDatabaseColumn(attribute: Map<String, String>?): String? =
        attribute?.let {
            // Map 요소 순서 변경에 의한 Update 방지를 위해 sortedMap 사용
            objectMapper.writeValueAsString(attribute.toSortedMap())
        }

    override fun convertToEntityAttribute(dbData: String?): Map<String, String>? =
        dbData?.let {
            objectMapper.readValue(it, object : TypeReference<Map<String, String>>() {})
        } ?: emptyMap()
}
