package com.nilgil.commerce.order

import com.nilgil.commerce.common.error.CoreException
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class OrderTest {
    @Nested
    @DisplayName("주문 생성 시")
    inner class DescribeCreation {
        @Test
        fun `CREATED 상태로 생성된다`() {
            // given, when
            val order = OrderFixtures.anOrder()

            // then
            assertThat(order.status).isEqualTo(OrderStatus.CREATED)
        }

        @Test
        fun `주문 항목에 중복된 상품이 존재하면 IllegalArgumentException이 발생한다`() {
            // given
            val productItemId = 1L
            val duplicateProductItemLines =
                listOf(
                    OrderFixtures.anOrderLine(item = OrderFixtures.anOrderItem(productItemId = productItemId)),
                    OrderFixtures.anOrderLine(item = OrderFixtures.anOrderItem(productItemId = productItemId)),
                )

            // when, then
            assertThatThrownBy {
                OrderFixtures.anOrder(lines = duplicateProductItemLines)
            }.isInstanceOf(IllegalArgumentException::class.java)
        }

        @Test
        fun `주문 총 금액이 0 미만이면 IllegalArgumentException이 발생한다`() {
            val mockOrderLine = mockk<OrderLine>()
            val mockItem = mockk<OrderItem>()

            every { mockOrderLine.item } returns mockItem
            every { mockOrderLine.productItemId } returns 1L
            every { mockOrderLine.totalPrice } returns -1000

            assertThatThrownBy {
                OrderFixtures.anOrder(lines = listOf(mockOrderLine))
            }.isInstanceOf(IllegalArgumentException::class.java)
        }
    }

    @Nested
    @DisplayName("결제 처리 시")
    inner class DescribePay {
        @Test
        fun `CREATED 상태이면 PAID로 정상 변경된다`() {
            // given
            val order = OrderFixtures.anOrder()

            // when
            order.pay(paymentId = 123L)

            // then
            assertThat(order.status).isEqualTo(OrderStatus.PAID)
            assertThat(order.paymentId).isEqualTo(123L)
        }

        @Test
        fun `CREATED 상태가 아니면 INVALID_STATUS_TRANSITION 에러가 발생한다`() {
            // given
            val order = OrderFixtures.anOrder()
            order.pay(paymentId = 123L)

            // when, then
            assertThatThrownBy { order.pay(paymentId = 456L) }
                .isInstanceOf(CoreException::class.java)
                .extracting { it as CoreException }
                .extracting(CoreException::type)
                .isEqualTo(OrderError.INVALID_STATUS_TRANSITION)
        }
    }

    @Nested
    @DisplayName("완료 처리 시")
    inner class DescribeComplete {
        @Test
        fun `PAID 상태이면 COMPLETED로 정상 변경된다`() {
            // given
            val order = OrderFixtures.anOrder()
            order.pay(100L)

            // when
            order.complete()

            // then
            assertThat(order.status).isEqualTo(OrderStatus.COMPLETED)
        }

        @Test
        fun `PAID 상태가 아니면 INVALID_STATUS_TRANSITION 에러가 발생한다`() {
            // given
            val order = OrderFixtures.anOrder()

            // when, then
            assertThatThrownBy { order.complete() }
                .isInstanceOf(CoreException::class.java)
                .extracting { it as CoreException }
                .extracting(CoreException::type)
                .isEqualTo(OrderError.INVALID_STATUS_TRANSITION)
        }
    }

    @Nested
    @DisplayName("취소 처리 시")
    inner class DescribeCancel {
        @Test
        fun `CREATED 상태이면 CANCELLED로 정상 변경된다`() {
            // given
            val createdOrder = OrderFixtures.anOrder()

            // when
            createdOrder.cancel()

            // then
            assertThat(createdOrder.status).isEqualTo(OrderStatus.CANCELLED)
        }

        @Test
        fun `PAID 상태이면 CANCELLED로 정상 변경된다`() {
            // given
            val paidOrder = OrderFixtures.anOrder()
            paidOrder.pay(123L)

            // when
            paidOrder.cancel()

            // then
            assertThat(paidOrder.status).isEqualTo(OrderStatus.CANCELLED)
        }

        @Test
        fun `COMPLETED 상태이면 INVALID_STATUS_TRANSITION 에러가 발생한다`() {
            // given
            val order = OrderFixtures.anOrder()
            order.pay(123L)
            order.complete()

            // when, then
            assertThatThrownBy { order.cancel() }
                .isInstanceOf(CoreException::class.java)
                .extracting { it as CoreException }
                .extracting(CoreException::type)
                .isEqualTo(OrderError.INVALID_STATUS_TRANSITION)
        }

        @Test
        fun `RETURNED 상태이면 INVALID_STATUS_TRANSITION 에러가 발생한다`() {
            // given
            val order = OrderFixtures.anOrder()
            order.pay(123L)
            order.complete()
            order.returnOrder()

            // when, then
            assertThatThrownBy { order.cancel() }
                .isInstanceOf(CoreException::class.java)
                .extracting { it as CoreException }
                .extracting(CoreException::type)
                .isEqualTo(OrderError.INVALID_STATUS_TRANSITION)
        }
    }

    @Nested
    @DisplayName("반품 처리 시")
    inner class DescribeReturnOrder {
        @Test
        fun `COMPLETED 상태이면 RETURNED로 정상 변경된다`() {
            // given
            val order = OrderFixtures.anOrder()
            order.pay(123L)
            order.complete()

            // when
            order.returnOrder()

            // then
            assertThat(order.status).isEqualTo(OrderStatus.RETURNED)
        }

        @Test
        fun `COMPLETED 상태가 아니면 INVALID_STATUS_TRANSITION 에러가 발생한다`() {
            // given
            val order = OrderFixtures.anOrder()

            // when, then
            assertThatThrownBy { order.returnOrder() }
                .isInstanceOf(CoreException::class.java)
                .extracting { it as CoreException }
                .extracting(CoreException::type)
                .isEqualTo(OrderError.INVALID_STATUS_TRANSITION)
        }
    }
}
