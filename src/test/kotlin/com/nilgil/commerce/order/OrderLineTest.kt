package com.nilgil.commerce.order

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class OrderLineTest {
    @Nested
    @DisplayName("객체 생성 시")
    inner class DescribeCreation {
        @CsvSource("0", "-1", Integer.MIN_VALUE.toString())
        @ParameterizedTest
        fun `수량이 0 또는 음수이면 IllegalArgumentException이 발생한다`(quantity: Int) {
            // when, then
            assertThatIllegalArgumentException()
                .isThrownBy {
                    OrderFixtures.anOrderLine(quantity = quantity)
                }
        }

        @CsvSource("1", "50", "100")
        @ParameterizedTest
        fun `수량이 1 이상 100 이하이면 정상적으로 생성된다`(quantity: Int) {
            // when
            val orderLine = OrderFixtures.anOrderLine(quantity = quantity)

            // then
            assertThat(orderLine).isNotNull
            assertThat(orderLine.quantity).isEqualTo(quantity)
        }

        @CsvSource("101", Integer.MAX_VALUE.toString())
        @ParameterizedTest
        fun `수량이 100 초과이면 IllegalArgumentException이 발생한다`(quantity: Int) {
            // when, then
            assertThatIllegalArgumentException()
                .isThrownBy {
                    OrderFixtures.anOrderLine(quantity = quantity)
                }
        }
    }

    @Nested
    @DisplayName("총액 조회 시")
    inner class DescribeGetTotalPrice {
        @Test
        fun `아이템 가격과 수량을 곱한 총액을 반환한다`() {
            // given
            val price = 15000
            val item = OrderFixtures.anOrderItem(price = price)

            val quantity = 3
            val orderLine = OrderFixtures.anOrderLine(item = item, quantity = quantity)

            // when
            val totalPrice = orderLine.totalPrice

            // then
            assertThat(totalPrice).isEqualTo(price * quantity)
        }
    }
}
