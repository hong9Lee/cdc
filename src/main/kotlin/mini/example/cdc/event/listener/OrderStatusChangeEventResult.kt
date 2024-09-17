package mini.example.cdc.event.listener

import mini.example.cdc.domain.enum.OrderStatus

data class OrderStatusChangeEventResult(
    val orderId: String,
    val orderStatus: OrderStatus,
    val isSuccess: Boolean
)
