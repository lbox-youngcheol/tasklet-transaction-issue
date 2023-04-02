package lbox.message

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface ScheduledMessageRepository : JpaRepository<ScheduledMessage, Long> {
    fun findAllByScheduledDateAndSent(scheduledDate: LocalDate, sent: Boolean): List<ScheduledMessage>
}
