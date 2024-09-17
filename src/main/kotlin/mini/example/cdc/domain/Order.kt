package mini.example.cdc.domain

import mini.example.cdc.domain.enum.OrderStatus

data class Order(
    val id: Long,
    val orderId: String,
    var orderStatus: OrderStatus
) {
    fun changeOrderStatus(orderStatus: OrderStatus) {
        this.orderStatus = orderStatus
    }
}
