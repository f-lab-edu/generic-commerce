package com.nilgil.commerce.common

import java.util.concurrent.TimeUnit

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DistributedLock(
    val prefix: String = "",
    val key: String,
    val waitTime: Long = 5,
    val timeUnit: TimeUnit = TimeUnit.SECONDS,
)
