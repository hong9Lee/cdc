package mini.example.cdc.event.listener

import io.github.oshai.kotlinlogging.KotlinLogging
import mini.example.cdc.event.OrderStatusChangeEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.event.EventListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

private val logger = KotlinLogging.logger {}

@Component
class OrderStatusChangeEventListener(
    private val kafkaTemplate: KafkaTemplate<String, OrderStatusChangeEvent>,
) {
    @Value("\${spring.kafka.topics.order-change-request}")
    private lateinit var topic: String

    @Async
    @EventListener
    fun handleOrderStatusChangeEvent(event: OrderStatusChangeEvent): CompletableFuture<OrderStatusChangeEventResult> {
        logger.info { "handleOrderStatusChangeEvent" }

        return kafkaTemplate.send(topic, event)
            .thenApply {
                OrderStatusChangeEventResult(
                    orderId = event.orderId,
                    orderStatus = event.orderStatus,
                    isSuccess = true
                )
            }
            .exceptionally {
                OrderStatusChangeEventResult(
                    orderId = event.orderId,
                    orderStatus = event.orderStatus,
                    isSuccess = false
                )
            }
    }
}
