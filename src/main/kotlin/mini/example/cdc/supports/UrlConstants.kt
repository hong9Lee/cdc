package mini.example.cdc.supports

class UrlConstants {
    companion object {
        const val 단순_주문_상태_변경 = "/api/order/v1/status"
        const val 주문_상태_변경_outbox = "/api/order/v1/status/outbox"
    }
}

