package mini.example.cdc.controller

import mini.example.cdc.controller.data.OrderStatusChangeRequest
import mini.example.cdc.service.OrderChangeFacadeService
import mini.example.cdc.supports.UrlConstants
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(
    private val orderChangeFacadeService: OrderChangeFacadeService
) {

    @PutMapping(UrlConstants.단순_주문_상태_변경)
    fun orderStatusChange(
        request: OrderStatusChangeRequest
    ) {
        orderChangeFacadeService.changeOrderStatus(
            request = request
        )
    }

    @GetMapping(UrlConstants.주문_상태_변경_outbox)
    fun orderStatusChangeOutbox(
        request: OrderStatusChangeRequest
    ) {
        orderChangeFacadeService.changeOrderStatusByOutbox(
            request = request
        )
    }
}
