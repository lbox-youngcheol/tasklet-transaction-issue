package lbox.batch

import lbox.message.ScheduledMessage
import lbox.message.ScheduledMessageRepository
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate

@Configuration
class SendScheduledMessageBatchConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val scheduledMessageRepository: ScheduledMessageRepository,
    private val sendScheduledMessageBatchProcessor: SendScheduledMessageBatchProcessor,
) {

    @Bean
    fun sendScheduledMessageJob(): Job =
        this.jobBuilderFactory["example-job"]
            .incrementer(RunIdIncrementer())
            .start(this.sendScheduledMessageJobStep())
            .build()

    @JobScope
    @Bean
    fun sendScheduledMessageJobStep(@Value("#{jobParameters[date]}") inputDate: String? = null): Step {
        val date = LocalDate.parse(inputDate)

        return this.stepBuilderFactory["example-job-step"]
            .tasklet { contribution, chunkContext ->
                val scheduledMessagesToBeSent = this.scheduledMessageRepository.findAllByScheduledDateAndSent(
                    scheduledDate = date,
                    sent = false,
                )

                for (scheduledMessage in scheduledMessagesToBeSent) {
                    try {
                        // this.scheduledMessagesService.send(scheduledMessage)
                        this.sendScheduledMessageBatchProcessor.process(scheduledMessage)
                    } catch (e: Exception) {
                        println("오류 발생 - id: ${scheduledMessage.id}, error message: ${e.message}")
                        e.printStackTrace()
                        this.sendBatchErrorMessageViaSlack(scheduledMessage, e)
                    }
                }

                RepeatStatus.FINISHED
            }
            .build()
    }

    private fun sendBatchErrorMessageViaSlack(scheduledMessage: ScheduledMessage, exception: Exception) {
        println("[Slack] 오류 발생 - id: ${scheduledMessage.id}, error message: ${exception.message}")
    }
}
