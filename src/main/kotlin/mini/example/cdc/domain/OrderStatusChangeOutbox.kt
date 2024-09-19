package mini.example.cdc.domain

import mini.example.cdc.domain.enum.OrderStatus
import mini.example.cdc.domain.enum.OutboxStatus
import java.util.*

data class OrderStatusChangeOutbox(
    val id: Long?,
    val orderStatus: OrderStatus,
    val orderId: String = UUID.randomUUID().toString(),
    val outboxId: String = UUID.randomUUID().toString(),
    var outboxStatus: OutboxStatus,
) {
    fun updateOutboxStatusSendSuccess() {
        this.outboxStatus = OutboxStatus.SEND_SUCCESS
    }

    fun updateOutboxStatusSendFail() {
        this.outboxStatus = OutboxStatus.SEND_FAIL
    }

    companion object {
        fun toInit(orderStatus: OrderStatus, orderId: String): OrderStatusChangeOutbox {
            return OrderStatusChangeOutbox(
                id = null,
                orderId = orderId,
                orderStatus = orderStatus,
                outboxId = UUID.randomUUID().toString(),
                outboxStatus = OutboxStatus.INIT,
            )
        }
    }
}
