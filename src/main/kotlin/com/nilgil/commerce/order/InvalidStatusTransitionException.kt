package com.nilgil.commerce.order

import com.nilgil.commerce.common.error.CoreException

class InvalidStatusTransitionException(
    val current: OrderStatus,
    val target: OrderStatus,
) : CoreException(
        errorType = OrderError.INVALID_STATUS_TRANSITION,
        logMessage = {
            "상태를 '$current'에서 '$target'로 변경할 수 없습니다."
        },
    )
