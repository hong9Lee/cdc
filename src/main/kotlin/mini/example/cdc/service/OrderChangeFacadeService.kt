package mini.example.cdc.service

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import mini.example.cdc.controller.data.OrderStatusChangeRequest
import mini.example.cdc.event.OrderStatusChangeEvent
import mini.example.cdc.event.OrderStatusChangeRecordEvent
import mini.example.cdc.repository.adapter.OrderAdapter
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionSynchronizationManager

private val logger = KotlinLogging.logger {}

@Service
@Transactional
class OrderChangeFacadeService(
    private val orderAdapter: OrderAdapter,
    private val eventPublisher: ApplicationEventPublisher
) {

    fun changeOrderStatus(request: OrderStatusChangeRequest) {
        logger.info { "changeOrderStatus init ${ TransactionSynchronizationManager.getCurrentTransactionName()}" }
        /** 1. 도메인 로직 */
        updateOrderStatus(request)
    }

    fun changeOrderStatusByOutbox(
        request: OrderStatusChangeRequest
    ) {
        logger.info { "init ${ TransactionSynchronizationManager.getCurrentTransactionName()}" }

        /** 1. 도메인 로직 */
        updateOrderStatus(request)

        /** 2. outbox 테이블에 기록 */
        publishOrderStatusChangeOutboxRecord(request)

        /** 2. 이벤트 발행 */
        publishOrderStatusChangeEvent(request)
    }


    private fun updateOrderStatus(request: OrderStatusChangeRequest) {
        val order = orderAdapter.findByOrderId(
            orderId = request.orderId
        )
        order.changeOrderStatus(request.orderStatus)
        orderAdapter.saveOrder(order)
    }

    private fun publishOrderStatusChangeOutboxRecord(request: OrderStatusChangeRequest) {
        logger.info { "publishOrderStatusChangeOutboxRecord" }

        eventPublisher.publishEvent(
            OrderStatusChangeRecordEvent(
                orderId = request.orderId,
                orderStatus = request.orderStatus
            )
        )
    }


    private fun publishOrderStatusChangeEvent(request: OrderStatusChangeRequest) {
        logger.info { "publishOrderStatusChangeEvent" }

        eventPublisher.publishEvent(
            OrderStatusChangeEvent(
                orderId = request.orderId,
                orderStatus = request.orderStatus
            )
        )
    }
}
