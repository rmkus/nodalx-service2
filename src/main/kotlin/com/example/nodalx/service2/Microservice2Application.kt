package com.example.nodalx.service2

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@SpringBootApplication
@EnableScheduling
class Microservice2Application {

    @Autowired
    lateinit var rabbitTemplate: RabbitTemplate

    companion object {
        const val EXCHANGE_NAME = "microserviceExchange"
        const val ROUTING_KEY = "microserviceKey"
    }

//    @Scheduled(fixedDelay = 10000, initialDelay = 20000)
//    fun sendPing() {
//        println("Sending ping...")
//        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, "ping")
//    }

    @RabbitListener(queues = ["microserviceQueue"])
    fun receiveMessage(message: String) {
        println("Service2:Received message: $message")
        if (message == "ping") {
            println("Service2:Sending pong...")
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, "pong")
            Thread.sleep(10000)
            println("Service2:Sending ping after 10 seconds...")
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, "ping")
        }
    }
}

fun main(args: Array<String>) {
    runApplication<Microservice2Application>(*args)
}
