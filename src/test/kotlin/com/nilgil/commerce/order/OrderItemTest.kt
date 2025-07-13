package com.nilgil.commerce.order

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class OrderItemTest {
    @Nested
    @DisplayName("객체 생성 시")
    inner class DescribeCreation {
        @Test
        fun `가격이 음수이면 IllegalArgumentException이 발생한다`() {
            // when, then
            assertThatIllegalArgumentException()
                .isThrownBy {
                    OrderFixtures.anOrderItem(price = -10000)
                }
        }

        @Test
        fun `가격이 0 또는 양수이면 정상적으로 생성된다`() {
            // when
            val itemWithZeroPrice = OrderFixtures.anOrderItem(price = 0)
            val itemWithPositivePrice = OrderFixtures.anOrderItem(price = 10000)

            // then
            assertThat(itemWithZeroPrice).isNotNull
            assertThat(itemWithPositivePrice).isNotNull
        }
    }
}
