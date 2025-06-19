package com.nilgil.commerce.common

import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class PositionableBaseEntity<T> :
    BaseEntity(),
    Comparable<T> {
    abstract var position: Int

    override fun compareTo(other: T): Int = compareValuesBy(this, other, { position }, { createdAt })

    fun changePosition(position: Int) {
        this.position = position
    }
}
