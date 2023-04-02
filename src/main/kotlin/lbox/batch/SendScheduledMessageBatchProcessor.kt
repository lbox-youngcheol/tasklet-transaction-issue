package lbox.batch

import lbox.message.ScheduledMessage
import lbox.message.SendScheduledMessagesService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class SendScheduledMessageBatchProcessor(
    private val scheduledMessagesService: SendScheduledMessagesService,
) {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun process(scheduledMessage: ScheduledMessage) {
        this.scheduledMessagesService.send(scheduledMessage)
    }
}
