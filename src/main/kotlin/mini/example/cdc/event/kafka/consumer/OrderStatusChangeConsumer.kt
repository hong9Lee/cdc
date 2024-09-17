package mini.example.cdc.event.kafka.consumer

import io.github.oshai.kotlinlogging.KotlinLogging
import mini.example.cdc.event.OrderStatusChangeEvent
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class OrderStatusChangeConsumer(
) {

    @KafkaListener(
        topics = ["\${spring.kafka.topics.order-change-request}"],
        groupId = "\${spring.kafka.consumer.group-id}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    fun listen(message: ConsumerRecord<String, OrderStatusChangeEvent>) {
        val orderStatusChangeEvent = message.value()
        logger.info { orderStatusChangeEvent }
        logger.info { "주문 상태 변경에 따른 알림톡 전송 ==> ${orderStatusChangeEvent.orderId}" }
    }

}
