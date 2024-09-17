package mini.example.cdc.domain.enum

enum class OrderStatus(
    val value: String
) {
    CREATE("생성"),
    DELIVERING("배송"),
    DELIVERY_COMPLETE("배송 완료"),
    COMPLETE("주문 완료")
}
