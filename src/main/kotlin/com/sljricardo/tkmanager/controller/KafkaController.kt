package com.sljricardo.tkmanager.controller

import com.sljricardo.tkmanager.events.KafkaProducerService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/kafka")
class KafkaController(private val kafkaProducerService: KafkaProducerService) {

    @PostMapping("/send")
    fun sendMessage(@RequestParam message: String): ResponseEntity<String> {
        kafkaProducerService.sendMessage("test-topic", message)
        return ResponseEntity.ok("Message sent to Kafka!")
    }
}