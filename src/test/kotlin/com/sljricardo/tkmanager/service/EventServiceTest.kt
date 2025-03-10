package com.sljricardo.tkmanager.service

import com.sljricardo.tkmanager.dto.EventResponse
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

class EventServiceTest {

    private lateinit var eventService: EventService

    @BeforeEach
    fun setUp() {
        eventService = EventService()
    }

    @Test
    fun `should subscribe a user and return emitter event`() {
        val userId = "user123"
        val emmiter = eventService.subscribe(userId)

        assertNotNull(emmiter)
    }

    @Test
    fun `should emit event to subscribed user`() {
        val userId = "user123"
        val message = "Test Event"
        val emitter = spy(SseEmitter(Long.MAX_VALUE))

        val emittersField = eventService.javaClass.getDeclaredField("emitters")
        emittersField.isAccessible = true
        val emitters = emittersField.get(eventService) as MutableMap<String, SseEmitter>
        emitters[userId] = emitter

        eventService.emit(message, userId)

        verify(emitter).send(EventResponse(userId, message)) // Verify message is sent
    }

    @Test
    fun `should remove emitter when completed`() {
        val userId = "user123"
        val emitter = eventService.subscribe(userId)

        emitter.complete()
        eventService.emit("Test Event", userId)

        // No error should occur even if emitter is completed
    }
}