package mini.example.cdc.domain.enum

enum class OutboxStatus(
    val value: String
) {
    INIT("생성"),
    SEND_SUCCESS("이벤트 전송 성공"),
    SEND_FAIL("이벤트 전송 실패"),
}
