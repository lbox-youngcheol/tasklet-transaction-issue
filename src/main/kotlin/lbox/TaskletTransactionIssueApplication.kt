package lbox

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableBatchProcessing
@SpringBootApplication
class TaskletTransactionIssueApplication

fun main(args: Array<String>) {
    runApplication<TaskletTransactionIssueApplication>(*args)
}
