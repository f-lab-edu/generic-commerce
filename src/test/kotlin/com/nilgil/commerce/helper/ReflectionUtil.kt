package com.nilgil.commerce.helper

internal fun <T : Any> T.forceSet(
    fieldName: String,
    value: Any,
) {
    val field = this::class.java.superclass.getDeclaredField(fieldName)
    field.isAccessible = true
    field.set(this, value)
}
