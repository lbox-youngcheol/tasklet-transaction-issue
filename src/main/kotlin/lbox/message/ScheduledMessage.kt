package lbox.message

import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "scheduled_message")
class ScheduledMessage(
    description: String,
    scheduledDate: LocalDate,
) {
    @Id
    val id: Long = 0

    @Column(name = "description")
    var description: String = description // 예약 메세지에 대한 설명
        protected set

    @Column(name = "scheduled_date")
    val scheduledDate: LocalDate = scheduledDate // 발송될 날짜

    @Column(name = "sent")
    var sent: Boolean = false // 발송 여부
        protected set

    @Column(name = "sent_at")
    var sentAt: LocalDateTime? = null // 발송 일시
        protected set

    fun updateDescription(description: String) {
        this.description = description
    }

    fun markAsSent() {
        this.sent = true
        this.sentAt = LocalDateTime.now()
    }
}
