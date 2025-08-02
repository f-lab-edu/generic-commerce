package com.nilgil.commerce.order

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class OrderController(
    private val orderFacade: OrderFacade,
) {
    @PostMapping("/orders")
    fun createOrder(
        userId: Long,
        @RequestBody request: CreateOrderRequest,
    ): ResponseEntity<CreateOrderResponse> {
        val response = orderFacade.createOrder(userId, request)
        val location = URI.create("/orders/${response.code}")
        return ResponseEntity.created(location).body(response)
    }
}
