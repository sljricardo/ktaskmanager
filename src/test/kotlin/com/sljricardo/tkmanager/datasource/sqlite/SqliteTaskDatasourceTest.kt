package com.sljricardo.tkmanager.datasource.sqlite

import com.sljricardo.tkmanager.model.*
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.sqlite.SQLiteDataSource

class SqliteTaskDatasourceTest {
    private lateinit var dsl: DSLContext
    private lateinit var datasource: SqliteTaskDatasource

    fun createMockTaskWithId(id: String = "task-id-test"): Task {
        return Task(
            id = id,
            name = "task-name",
            description = "task-description",
            assignee = null
        )
    }

    @BeforeEach
    fun setup() {
        // Create an in-memory SQLite database
        val dataSource = SQLiteDataSource().apply {
            url = "jdbc:sqlite::memory:"
        }

        dsl = DSL.using(dataSource.connection) // JOOQ DSLContext
        datasource = SqliteTaskDatasource(dsl)

        // Create the USERS table
        dsl.execute("""
            CREATE TABLE TASK (
                ID TEXT PRIMARY KEY NOT NULL,
                NAME TEXT NOT NULL,
                DESCRIPTION TEXT,
                ASSIGNEE_ID TEXT,
                FOREIGN KEY (ASSIGNEE_ID) REFERENCES USERS(ID) ON DELETE SET NULL
            );
        """.trimIndent())
        // Create the USERS table also needed due to assigne_id
        dsl.execute("""
            CREATE TABLE USERS (
                ID TEXT PRIMARY KEY,
                NAME TEXT NOT NULL
            );
        """.trimIndent())
    }

    @AfterEach
    fun tearDown() {
        dsl.execute("DROP TABLE TASK;") // Cleanup after each test
        dsl.execute("DROP TABLE USERS;") // Cleanup after each test
    }

    @Test
    fun `should create a task`() {
        datasource.newTask(
            createMockTaskWithId("task-id-test")
        )

        val task = datasource.retrieveTask("task-id-test")

        assertNotNull(task)
    }

    @Test
    fun `should retrieve a created task`() {
        datasource.newTask(
            createMockTaskWithId("task-id-test2")
        )

        val task = datasource.retrieveTask("task-id-test2")

        assertEquals("task-id-test2", task?.id)
    }

    @Test
    fun `should return null if task do not exist`() {
        datasource.newTask(
            createMockTaskWithId("task-id-test-exists")
        )

        val task = datasource.retrieveTask("task-id-test-not-exists")

        assertNull(task)
    }

}