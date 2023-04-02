package lbox.message

import org.springframework.stereotype.Service
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Service
class SendScheduledMessagesService(
    @PersistenceContext
    private val entityManager: EntityManager,
    private val scheduledMessageRepository: ScheduledMessageRepository,
) {
    fun send(scheduledMessage: ScheduledMessage) {
        this.sendMessage(scheduledMessage)
        scheduledMessage.markAsSent()

        if (scheduledMessage.id == 3L) {
            scheduledMessage.updateDescription("특별히 긴 문장".repeat(10))
        }

        this.scheduledMessageRepository.save(scheduledMessage)
        this.entityManager.flush() // 바로 UPDATE 쿼리를 호출하도록 직접 EntityManager#flush를 호출함
    }

    private fun sendMessage(scheduledMessage: ScheduledMessage) {
        println("메세지 전송 - id: ${scheduledMessage.id}")
    }
}
