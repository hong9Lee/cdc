package mini.example.cdc.kafka.consumer

import io.github.oshai.kotlinlogging.KotlinLogging
import mini.example.cdc.domain.enum.OrderStatus
import mini.example.cdc.event.OrderStatusChangeEvent
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class OrderStatusChangeOutboxListener(
) {

    @KafkaListener(
        topics = ["\${spring.kafka.topics.order-change-request}"],
        groupId = "\${spring.kafka.consumer.group-id}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    fun listen(message: ConsumerRecord<String, Map<String, Any>>) {
        val event = message.value()
        logger.info { "Received CDC Event: $event" }
        // TODO: consume json
    }

}
