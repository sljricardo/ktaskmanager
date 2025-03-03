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

    private fun createMockTaskWithId(id: String = "task-id-test", name: String = "task-name"): Task {
        return Task(
            id = id,
            name = name,
            description = "task-description",
            assignee = null,
            state = TaskState.TODO
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
                STATE TEXT NOT NULL,
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
        // Create a USER
        dsl.execute("""
            INSERT INTO USERS (ID, NAME)
            VALUES ('test-id-mock', 'test user name')
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

    @Test
    fun `return a collection of tasks`() {
        datasource.newTask(
            createMockTaskWithId("task-id-test")
        )

        val tasks = datasource.retrieveTasks()

        assertTrue(tasks.isNotEmpty())
        assertEquals(tasks.first().id, "task-id-test")
    }

    @Test
    fun `should return partial search tasks`() {
        datasource.newTask(
            createMockTaskWithId(
                id = "test-id",
                name = "task-name-search"
            )
        )

        val tasks = datasource.searchTask("search")

        assertTrue(tasks.size == 1)
    }

    @Test
    fun `should return an empty collection if search do not find tasks`() {
        datasource.newTask(
            createMockTaskWithId(
                id = "test-id",
                name = "task-name-search"
            )
        )
        datasource.newTask(
            createMockTaskWithId(
                id = "test-id-2",
                name = "task-name-search-2"
            )
        )

        val tasks = datasource.searchTask("nothing")

        assertTrue(tasks.isEmpty())
    }

    @Test
    fun `should remove a task`() {
        datasource.newTask(
            createMockTaskWithId("task-id-1")
        )
        datasource.newTask(
            createMockTaskWithId("task-id-2")
        )

        datasource.removeTask("task-id-2")

        val tasks = datasource.retrieveTasks()

        assertTrue(tasks.size == 1)
    }

    @Test
    fun `should assign task to a user`() {
        datasource.newTask(
            createMockTaskWithId("task-id-1")
        )
        datasource.assignTask("task-id-1", User(
            id = "test-id-mock",
            name = "test user name"
        ))

        val task = datasource.retrieveTask("task-id-1")

        assertNotNull(task?.assignee)
        assertEquals(task?.assignee?.name, "test user name")
    }
}