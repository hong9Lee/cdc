package mini.example.cdc.repository.adapter

import mini.example.cdc.domain.Order
import mini.example.cdc.domain.entity.OrderEntity
import mini.example.cdc.repository.OrderRepository
import org.springframework.stereotype.Component

@Component
class OrderAdapter(
    private val orderRepository: OrderRepository
) {

    fun findByOrderId(orderId: String): Order {
        return orderRepository.findByOrderId(orderId = orderId).toDomain()
    }

    fun saveOrder(order: Order): Order {
        return orderRepository.save(
            OrderEntity.of(order)
        ).toDomain()
    }
}
