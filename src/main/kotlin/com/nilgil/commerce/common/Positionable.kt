package com.nilgil.commerce.common

interface Positionable<T : Positionable<T>> : Comparable<T> {
    val position: Int

    override fun compareTo(other: T): Int = this.position.compareTo(other.position)
}
