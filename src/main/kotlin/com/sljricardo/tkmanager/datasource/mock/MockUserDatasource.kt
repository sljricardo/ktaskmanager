package com.sljricardo.tkmanager.datasource.mock

import com.sljricardo.tkmanager.datasource.UserDatasource
import com.sljricardo.tkmanager.model.User
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Profile("local")
class MockUserDatasource: UserDatasource {
    private val users = mutableListOf(
        User(
            id = UUID.randomUUID().toString(),
            name = "Name of the user"
        )
    )

    override fun newUser(user: User): User {
        users.add(user)
        return user
    }

    override fun retrieveUser(userId: String): User? = users.firstOrNull { userId == it.id }

    override fun retrieveUsers(): Collection<User> = users

}