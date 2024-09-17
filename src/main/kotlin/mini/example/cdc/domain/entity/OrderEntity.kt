package mini.example.cdc.domain.entity

import jakarta.persistence.*
import mini.example.cdc.domain.Order
import mini.example.cdc.domain.enum.OrderStatus
import java.time.ZonedDateTime

@Entity
@Table(name = "orders")
data class OrderEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq", nullable = false, updatable = false)
    val id: Long,

    @Column(name = "order_id")
    val orderId: String,

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    val orderStatus: OrderStatus,

    @Column(name = "reg_date_time", nullable = false, updatable = false)
    private var regDateTime: ZonedDateTime?= null,

    @Column(name = "mod_date_time", nullable = false)
    private var modDateTime: ZonedDateTime?= null
) {
    companion object {
        fun of(order: Order): OrderEntity {
            return OrderEntity(
                id = order.id,
                orderId = order.orderId,
                orderStatus = order.orderStatus
            )
        }
    }

    fun toDomain(): Order {
        return Order(
            id = this.id,
            orderId = this.orderId,
            orderStatus = this.orderStatus
        )
    }

    @PreUpdate
    fun onPreUpdate() {
        modDateTime = ZonedDateTime.now()
    }
}
