package com.nilgil.commerce.common

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.integration.redis.util.RedisLockRegistry
import org.springframework.integration.support.locks.LockRegistry
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import kotlin.time.Duration.Companion.seconds

@Configuration
class LockConfig {
    companion object {
        private const val LOCK_REGISTRY_KEY = "lock"
        private const val LOCK_LEASE_TIME_SECONDS = 30L
        private const val LOCK_WATCHDOG_COUNT = 4
    }

    @Bean
    fun redisLockRegistry(
        redisConnectionFactory: RedisConnectionFactory,
        lockWatchdogScheduler: TaskScheduler,
    ): LockRegistry =
        RedisLockRegistry(
            redisConnectionFactory,
            LOCK_REGISTRY_KEY,
            LOCK_LEASE_TIME_SECONDS.seconds.inWholeMilliseconds,
        ).apply {
            setRedisLockType(RedisLockRegistry.RedisLockType.PUB_SUB_LOCK)
            setRenewalTaskScheduler(lockWatchdogScheduler)
        }

    @Bean
    fun lockWatchdogScheduler(): TaskScheduler =
        ThreadPoolTaskScheduler().apply {
            poolSize = LOCK_WATCHDOG_COUNT
            setThreadNamePrefix("lock-watchdog-")
            initialize()
        }
}
