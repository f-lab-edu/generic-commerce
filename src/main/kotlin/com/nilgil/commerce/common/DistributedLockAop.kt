package com.nilgil.commerce.common

import com.nilgil.commerce.common.error.CoreError
import com.nilgil.commerce.common.error.CoreException
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.springframework.integration.support.locks.LockRegistry
import org.springframework.stereotype.Component

@Aspect
@Component
class DistributedLockAop(
    private val lockRegistry: LockRegistry,
) {
    @Around("@annotation(distributedLock)")
    fun lock(
        joinPoint: ProceedingJoinPoint,
        distributedLock: DistributedLock,
    ): Any? {
        val dynamicKey = resolveSpelKey(joinPoint, distributedLock.key)
        val lockKey = "${distributedLock.prefix}:$dynamicKey"

        val lock = lockRegistry.obtain(lockKey)

        if (!lock.tryLock(distributedLock.waitTime, distributedLock.timeUnit)) {
            throw CoreException(CoreError.FAIL_TO_GET_LOCK, detail = "key: ${distributedLock.key}")
        }

        try {
            return joinPoint.proceed()
        } finally {
            lock.unlock()
        }
    }

    private fun resolveSpelKey(
        joinPoint: ProceedingJoinPoint,
        key: String,
    ): String {
        val methodSignature = joinPoint.signature as MethodSignature
        val parameterNames = methodSignature.parameterNames
        val args = joinPoint.args

        val context = StandardEvaluationContext()
        parameterNames.zip(args).forEach { (name, value) ->
            context.setVariable(name, value)
        }

        val expressionParser = SpelExpressionParser()
        return expressionParser.parseExpression(key).getValue(context, String::class.java)
            ?: throw CoreException(CoreError.FAIL_TO_GET_LOCK, detail = "락 키를 파싱하는 데 실패했습니다: $key")
    }
}
