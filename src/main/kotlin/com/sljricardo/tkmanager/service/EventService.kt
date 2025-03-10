package com.sljricardo.tkmanager.service

import com.sljricardo.tkmanager.dto.EventResponse
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.ConcurrentHashMap

@Service
class EventService {
    private val emitters = ConcurrentHashMap<String, SseEmitter>()

    fun subscribe(userId: String): SseEmitter {
        val emitter = SseEmitter(Long.MAX_VALUE)
        emitters[userId] = emitter

        emitter.onCompletion { emitters.remove(userId) }
        emitter.onTimeout { emitters.remove(userId) }

        return emitter
    }

    fun emit(event: String, userId: String) {
        emitters[userId]?.let { emitter ->
            try {
                val event = EventResponse(userId, event)
                emitter.send(event)
            } catch (e: Exception) {
                emitter.complete()
                emitters.remove(userId)
            }
        }
    }
}