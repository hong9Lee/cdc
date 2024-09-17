package mini.example.cdc.service

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import mini.example.cdc.controller.data.OrderStatusChangeRequest
import mini.example.cdc.event.OrderStatusChangeEvent
import mini.example.cdc.repository.adapter.OrderAdapter
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
@Transactional
class OrderChangeFacadeService(
    private val orderAdapter: OrderAdapter,
    private val eventPublisher: ApplicationEventPublisher
) {

    fun changeOrderStatus(
        request: OrderStatusChangeRequest
    ) {
        val order = orderAdapter.findByOrderId(
            orderId = request.orderId
        )
        order.changeOrderStatus(request.orderStatus)
        orderAdapter.saveOrder(order)
        publishOrderStatusChangeEvent(request)
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
