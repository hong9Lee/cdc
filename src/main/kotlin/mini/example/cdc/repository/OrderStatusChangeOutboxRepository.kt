package mini.example.cdc.repository

import mini.example.cdc.domain.entity.OrderStatusChangeOutboxEntity
import org.springframework.data.repository.CrudRepository

interface OrderStatusChangeOutboxRepository : CrudRepository<OrderStatusChangeOutboxEntity, Long> {
    fun findByOrderId(orderId: String): OrderStatusChangeOutboxEntity
}
