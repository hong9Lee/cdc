package mini.example.cdc.kafka.consumer

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class OrderStatusChangeDebeziumListener {

    @KafkaListener(topics = ["mysql-order-.cdc.orders"], groupId = "cdc-consumer")
    fun consumeOrderChange(event: String) {
        logger.info { "Received CDC Event: $event" }
    }

}

