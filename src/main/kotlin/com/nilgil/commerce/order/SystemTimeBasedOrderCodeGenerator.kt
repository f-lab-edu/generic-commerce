package com.nilgil.commerce.order

import org.springframework.stereotype.Component

@Component
class SystemTimeBasedOrderCodeGenerator : OrderCodeGenerator {
    /**
     * 밀리 초 까지 겹치는 경우는 낙관적으로 처리한다.
     * 추후 트래픽 증가에 따라 공유 시퀀스 사용 등 검토가 필요하다.
     */
    override fun generate(): String = "ORDER-${System.currentTimeMillis()}"
}
