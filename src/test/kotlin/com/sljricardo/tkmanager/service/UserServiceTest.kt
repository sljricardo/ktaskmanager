package com.sljricardo.tkmanager.service

import com.sljricardo.tkmanager.datasource.UserDatasource
import com.sljricardo.tkmanager.model.User
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class UserServiceTest {

    private lateinit var datasource: UserDatasource
    private lateinit var userService: UserService

    @BeforeEach
    fun setup() {
        datasource = mock()
        userService = UserService(datasource)
    }

    @Test
    fun `should add a user successfully`() {
        val user = User(id = "test-user-id", name = "Test User")

        // Stub
        whenever(datasource.newUser(user)).thenReturn(user)

        val result = userService.addUser(user)

        assertEquals(result, user)
        verify(datasource, times(1)).newUser(user)
    }

    @Test
    fun `should be able to get a user by id`() {
        val user = User(id = "test-user-id", name = "Test User")

        whenever(datasource.retrieveUser("test-user-id")).thenReturn(user)

        val getUser = userService.getUser("test-user-id")

        assertEquals(getUser, user)
        verify(datasource).retrieveUser("test-user-id")
    }

    @Test
    fun `should return null if user not found`() {
        whenever(datasource.retrieveUser("not-found")).thenReturn(null)

        val getUser = userService.getUser("not-found")

        assertNull(getUser)
    }

    @Test
    fun `should retrieve all users`() {
        val usersList = listOf(
            User(id = "test-user-1", name = "Test User 1"),
            User(id = "test-user-2", name = "Test User 2")
        )

        whenever(datasource.retrieveUsers()).thenReturn(usersList)

        val users = userService.getUsers()

        assertNotNull(users)
        assertEquals(usersList, users)
        verify(datasource, times(1)).retrieveUsers()
    }
}