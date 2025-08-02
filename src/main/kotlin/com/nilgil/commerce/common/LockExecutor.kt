package com.nilgil.commerce.common

import com.nilgil.commerce.common.error.CoreError
import com.nilgil.commerce.common.error.CoreException
import org.springframework.integration.support.locks.LockRegistry
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class LockExecutor(
    private val lockRegistry: LockRegistry,
) {
    fun <T> execute(
        key: String,
        waitTime: Long = 5,
        timeUnit: TimeUnit = TimeUnit.SECONDS,
        callable: () -> T,
    ): T {
        val lock = lockRegistry.obtain(key)

        if (!lock.tryLock(waitTime, timeUnit)) {
            throw CoreException(CoreError.FAIL_TO_GET_LOCK, detail = "key: $key")
        }

        try {
            return callable()
        } finally {
            lock.unlock()
        }
    }
}
