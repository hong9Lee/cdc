package mini.example.cdc.controller.data

import mini.example.cdc.domain.enum.OrderStatus

data class OrderStatusChangeRequest(
    val orderId: String,
    val orderStatus: OrderStatus
)
