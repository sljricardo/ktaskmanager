package com.sljricardo.tkmanager.events

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaConsumerService {

    @KafkaListener(topics = ["test-topic"], groupId = "my-group")
    fun listen(message: String) {
        println("📥 Received message: '$message'")
    }
}