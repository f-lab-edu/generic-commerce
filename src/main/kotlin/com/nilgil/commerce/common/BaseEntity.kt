package com.nilgil.commerce.common

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @CreationTimestamp
    @Column(updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.MIN

    @UpdateTimestamp
    val updatedAt: LocalDateTime = LocalDateTime.MIN
}
