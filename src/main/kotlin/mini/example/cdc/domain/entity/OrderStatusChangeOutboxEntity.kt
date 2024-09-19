package mini.example.cdc.domain.entity

import jakarta.persistence.*
import mini.example.cdc.domain.OrderStatusChangeOutbox
import mini.example.cdc.domain.enum.OrderStatus
import mini.example.cdc.domain.enum.OutboxStatus
import java.time.ZonedDateTime
import java.util.*

@Entity
@Table(name = "orders_status_change_outbox")
data class OrderStatusChangeOutboxEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq", nullable = false, updatable = false)
    val id: Long? = null,

    @Column(name = "outbox_id")
    val outboxId: String = UUID.randomUUID().toString(),

    @Column(name = "order_id")
    val orderId: String,

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    val orderStatus: OrderStatus,

    @Column(name = "outbox_status")
    @Enumerated(EnumType.STRING)
    val outboxStatus: OutboxStatus,

    @Column(name = "reg_date_time", nullable = false, updatable = false)
    private var regDateTime: ZonedDateTime? = ZonedDateTime.now(),

    @Column(name = "mod_date_time", nullable = false)
    private var modDateTime: ZonedDateTime? = ZonedDateTime.now()
) {
    companion object {
        fun of(orderStatusChangeOutbox: OrderStatusChangeOutbox): OrderStatusChangeOutboxEntity {
            return OrderStatusChangeOutboxEntity(
                id = orderStatusChangeOutbox.id,
                orderStatus = orderStatusChangeOutbox.orderStatus,
                orderId = orderStatusChangeOutbox.orderId,
                outboxId = orderStatusChangeOutbox.outboxId,
                outboxStatus = orderStatusChangeOutbox.outboxStatus,
            )
        }
    }

    fun toDomain(): OrderStatusChangeOutbox {
        return OrderStatusChangeOutbox(
            id = this.id ?: 0L,
            orderStatus = this.orderStatus,
            outboxId = this.outboxId,
            outboxStatus = this.outboxStatus,
        )
    }

    @PreUpdate
    fun onPreUpdate() {
        modDateTime = ZonedDateTime.now()
    }
}
