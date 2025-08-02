package com.nilgil.commerce.common

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class StringListConverter : AttributeConverter<List<String>, String> {
    companion object {
        private const val DELIMITER = ","
    }

    override fun convertToDatabaseColumn(attribute: List<String>?): String? {
        if (attribute.isNullOrEmpty()) {
            return null
        }
        return attribute.joinToString(DELIMITER)
    }

    override fun convertToEntityAttribute(dbData: String?): List<String> {
        if (dbData.isNullOrBlank()) {
            return emptyList()
        }
        return dbData.split(DELIMITER).map { it.trim() }
    }
}
