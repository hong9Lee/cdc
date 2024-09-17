package mini.example.cdc.event

import mini.example.cdc.domain.enum.OrderStatus

data class OrderStatusChangeEvent(
    val orderId: String,
    val orderStatus: OrderStatus
)
