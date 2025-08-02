package com.nilgil.commerce.common

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.integration.redis.util.RedisLockRegistry
import org.springframework.integration.support.locks.LockRegistry

@Configuration
class LockConfig {
    companion object {
        private const val LOCK_REGISTRY_KEY = "lock"
    }

    @Bean
    fun redisLockRegistry(redisConnectionFactory: RedisConnectionFactory): LockRegistry =
        RedisLockRegistry(redisConnectionFactory, LOCK_REGISTRY_KEY)
            .apply { setRedisLockType(RedisLockRegistry.RedisLockType.PUB_SUB_LOCK) }
}
