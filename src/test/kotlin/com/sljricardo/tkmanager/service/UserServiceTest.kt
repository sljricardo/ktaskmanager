package com.sljricardo.tkmanager.service

import com.sljricardo.tkmanager.datasource.UserDatasource
import com.sljricardo.tkmanager.model.User
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.whenever
import kotlin.test.assertEquals

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
}