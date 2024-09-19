package mini.example.cdc.event.listener

import io.github.oshai.kotlinlogging.KotlinLogging
import mini.example.cdc.event.OrderStatusChangeEvent
import mini.example.cdc.repository.adapter.OrderStatusChangeOutboxAdapter
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionalEventListener
import org.springframework.transaction.support.TransactionSynchronizationManager

private val logger = KotlinLogging.logger {}

@Component
@Transactional
class OrderStatusChangeEventListener(
    private val kafkaTemplate: KafkaTemplate<String, OrderStatusChangeEvent>,
    private val orderStatusChangeOutboxAdapter: OrderStatusChangeOutboxAdapter
) {
    @Value("\${spring.kafka.topics.order-change-request}")
    private lateinit var topic: String

    @Async
    @TransactionalEventListener
    fun handleOrderStatusChangeEvent(event: OrderStatusChangeEvent) {
        logger.info { "handleOrderStatusChangeEvent ${TransactionSynchronizationManager.getCurrentTransactionName()}" }

        val orderStatusChangeEventResult = kafkaTemplate.send(topic, event)
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
            }.get()

        updateOrderStatusChangeOutbox(event.orderId, orderStatusChangeEventResult)
    }

    private fun updateOrderStatusChangeOutbox(
        orderId: String,
        orderStatusChangeEventResult: OrderStatusChangeEventResult
    ) {
        val orderStatusChangeOutbox = orderStatusChangeOutboxAdapter.findByOrderId(orderId)
        if (orderStatusChangeEventResult.isSuccess) {
            orderStatusChangeOutbox.updateOutboxStatusSendSuccess()
            logger.info { "orderStatusChangeOutbox update SEND SUCCESS " }
        } else {
            orderStatusChangeOutbox.updateOutboxStatusSendFail()
            logger.warn { "orderStatusChangeOutbox update SEND FAIL " }
        }

        orderStatusChangeOutboxAdapter.saveOutbox(orderStatusChangeOutbox)
    }
}
