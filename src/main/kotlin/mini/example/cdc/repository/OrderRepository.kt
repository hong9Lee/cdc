package mini.example.cdc.repository

import mini.example.cdc.domain.entity.OrderEntity
import org.springframework.data.repository.CrudRepository

interface OrderRepository: CrudRepository<OrderEntity, Long> {
    fun findByOrderId(orderId: String): OrderEntity
}
