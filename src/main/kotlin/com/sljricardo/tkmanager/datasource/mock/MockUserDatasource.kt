package com.sljricardo.tkmanager.datasource.mock

import com.sljricardo.tkmanager.datasource.UserDatasource
import com.sljricardo.tkmanager.model.User
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import kotlin.math.sqrt

@Repository
@Profile("local")
class MockUserDatasource: UserDatasource {
    private val users = mutableListOf(
        User(
            id = "1",
            name = "first"
        )
    )

    override fun newUser(user: User): User {
        users.add(user)
        return user
    }

    override fun retrieveUser(userId: String): User? = users.firstOrNull { userId == it.id }

}