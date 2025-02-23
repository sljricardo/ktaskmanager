package com.sljricardo.tkmanager.events

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducerService(private val kafkaTemplate: KafkaTemplate<String, String>) {

    fun sendMessage(topic: String, message: String) {
        kafkaTemplate.send(topic, message)
        println("✅ Sent message: '$message' to topic: '$topic'")
    }
}