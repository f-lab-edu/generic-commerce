package com.nilgil.commerce.order

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class OrderLineTest {
    @Nested
    @DisplayName("객체 생성 시")
    inner class DescribeCreation {
        @Test
        fun `수량이 0 또는 음수이면 IllegalArgumentException이 발생한다`() {
            // when & then
            assertThatIllegalArgumentException()
                .isThrownBy {
                    OrderFixtures.anOrderLine(quantity = 0)
                }
            assertThatIllegalArgumentException()
                .isThrownBy {
                    OrderFixtures.anOrderLine(quantity = -1)
                }
        }

        @Test
        fun `수량이 양수이면 정상적으로 생성된다`() {
            // when
            val orderLine = OrderFixtures.anOrderLine(quantity = 1)

            // then
            assertThat(orderLine).isNotNull
            assertThat(orderLine.quantity).isEqualTo(1)
        }
    }

    @Nested
    @DisplayName("총액 조회 시")
    inner class DescribeGetTotalPrice {
        @Test
        fun `아이템 가격과 수량을 곱한 총액을 반환한다`() {
            // given
            val item = OrderFixtures.anOrderItem(price = 15000)
            val orderLine = OrderFixtures.anOrderLine(item = item, quantity = 3)

            // when
            val totalPrice = orderLine.getTotalPrice()

            // then
            assertThat(totalPrice).isEqualTo(45000)
        }
    }
}
