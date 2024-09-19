package mini.example.cdc.repository.adapter

import mini.example.cdc.domain.OrderStatusChangeOutbox
import mini.example.cdc.domain.entity.OrderStatusChangeOutboxEntity
import mini.example.cdc.repository.OrderStatusChangeOutboxRepository
import org.springframework.stereotype.Component

@Component
class OrderStatusChangeOutboxAdapter(
    private val orderStatusChangeOutboxRepository: OrderStatusChangeOutboxRepository
) {
    fun saveOutbox(orderStatusChangeOutbox: OrderStatusChangeOutbox): OrderStatusChangeOutbox {
        return orderStatusChangeOutboxRepository.save(
            OrderStatusChangeOutboxEntity.of(orderStatusChangeOutbox)
        ).toDomain()
    }

    fun findByOrderId(orderId: String): OrderStatusChangeOutbox {
        return orderStatusChangeOutboxRepository.findByOrderId(orderId).toDomain()
    }
}
