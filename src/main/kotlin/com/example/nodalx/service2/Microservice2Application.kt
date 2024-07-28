package com.example.nodalx.service2

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootApplication
@EnableScheduling
class Microservice2Application {

    @Autowired
    lateinit var rabbitTemplate: RabbitTemplate
    var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    companion object {
        const val EXCHANGE_NAME = "NodalxExchange"
        const val ROUTING_KEY_1 = "nodalxeKey1"
        const val ROUTING_KEY_2 = "nodalxeKey2"
    }

    @RabbitListener(queues = ["nodalxQueue2"])
    fun receiveMessage(message: String) {
        val formattedTimestamp = LocalDateTime.now().format(formatter)
        println("[$formattedTimestamp] - Service2:Received message: $message")
        if (message == "ping") {
            println("[$formattedTimestamp] - Service2:Sending pong...")
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY_1, "pong")
            Thread.sleep(10000)
            println("[$formattedTimestamp] - Service2:Sending ping after 10 seconds...")
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY_1, "ping")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<Microservice2Application>(*args)
}
