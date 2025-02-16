package com.sljricardo.tkmanager.datasource.sqlite

import com.sljricardo.tkmanager.model.User
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.sqlite.SQLiteDataSource

class SqliteUserDatasourceTest {

    private lateinit var dsl: DSLContext
    private lateinit var datasource: SqliteUserDatasource

    private fun createMockUserWithId(id: String = "uuid-test-mock"): User {
        val user = User(id = id, name = "Test User")
        return datasource.newUser(user)
    }

    @BeforeEach
    fun setup() {
        // Create an in-memory SQLite database
        val dataSource = SQLiteDataSource().apply {
            url = "jdbc:sqlite::memory:"
        }

        dsl = DSL.using(dataSource.connection) // JOOQ DSLContext
        datasource = SqliteUserDatasource(dsl)

        // Create the USERS table
        dsl.execute("""
            CREATE TABLE USERS (
                ID TEXT PRIMARY KEY,
                NAME TEXT NOT NULL
            );
        """.trimIndent())
    }

    @AfterEach
    fun tearDown() {
        dsl.execute("DROP TABLE USERS;") // Cleanup after each test
    }

    @Test
    fun `should insert a new user`() {
        val user = User(id = "uuid-test-mock", name = "Test User")

        val result = datasource.newUser(user)

        assertEquals(user, result)

        val retrievedUser = datasource.retrieveUser(user.id)
        assertEquals(user.name, retrievedUser?.name)
    }

    @Test
    fun `should be able to get a created user by ID`() {
        createMockUserWithId("test-id")

        val searchedUser = datasource.retrieveUser("test-id")

        assertNotNull(searchedUser)
        assertEquals("test-id", searchedUser?.id)
    }

    @Test
    fun `should get a null if user do not exists`() {
        val searchedUser = datasource.retrieveUser("no-existing-id")

        assertNull(searchedUser)
    }

    @Test
    fun `should return a collection of Users`() {
        createMockUserWithId("test-id-1")
        createMockUserWithId("test-id-2")

        val usersCollection = datasource.retrieveUsers()

        assertTrue(usersCollection.size == 2)
    }
}
