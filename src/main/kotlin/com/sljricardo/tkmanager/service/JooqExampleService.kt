package com.sljricardo.tkmanager.service

import org.jooq.DSLContext
import org.springframework.stereotype.Service

@Service
class JooqExampleService(private val dsl: DSLContext) {

    fun exampleQuery(): String {
        // This is a very basic jOOQ query just to check if things are working
        val result = dsl.select(org.jooq.impl.DSL.`val`("Hello jOOQ with SQLite!"))
            .fetchOne()
            ?.into(String::class.java)

        return result ?: "jOOQ query failed"
    }
}