package com.sljricardo.tkmanager.controller

import com.sljricardo.tkmanager.service.JooqExampleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/check")
class CheckController(private val jooqExampleService: JooqExampleService) {
    @GetMapping
    fun check(): String {
        return jooqExampleService.exampleQuery()
    }
}