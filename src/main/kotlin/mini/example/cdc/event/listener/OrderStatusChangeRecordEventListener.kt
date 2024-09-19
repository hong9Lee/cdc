package mini.example.cdc.event.listener

import io.github.oshai.kotlinlogging.KotlinLogging
import mini.example.cdc.domain.OrderStatusChangeOutbox
import mini.example.cdc.event.OrderStatusChangeRecordEvent
import mini.example.cdc.repository.adapter.OrderStatusChangeOutboxAdapter
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import org.springframework.transaction.support.TransactionSynchronizationManager

private val logger = KotlinLogging.logger {}

@Component
class OrderStatusChangeRecordEventListener(
    private val orderStatusChangeOutboxAdapter: OrderStatusChangeOutboxAdapter
) {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    fun handleOrderStatusChangeOutboxEvent(event: OrderStatusChangeRecordEvent) {
        logger.info { "handleOrderStatusChangeOutboxEvent ${TransactionSynchronizationManager.getCurrentTransactionName()}" }

        val saveOutbox = orderStatusChangeOutboxAdapter.saveOutbox(
            OrderStatusChangeOutbox.toInit(event.orderStatus, event.orderId)
        )

        logger.info { "handleOrderStatusChangeOutboxEvent saved Outbox Event id ${saveOutbox.outboxId}" }
    }
}
