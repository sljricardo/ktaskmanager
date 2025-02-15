package com.sljricardo.tkmanager.datasource.sqlite

import com.sljricardo.jooq.tables.references.USERS
import com.sljricardo.tkmanager.datasource.UserDatasource
import com.sljricardo.tkmanager.model.User
import org.jooq.DSLContext
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import java.util.*


@Repository
@Profile("sqlite")
class SqliteUserDatasource(private val dsl: DSLContext): UserDatasource {
    override fun newUser(user: User): User {
        dsl.insertInto(USERS)
            .set(USERS.ID, user.id)
            .set(USERS.NAME, user.name)
            .execute()
        return user
    }

    override fun retrieveUser(userId: String): User? {
        return dsl.selectFrom(USERS)
            .where(USERS.ID.eq(userId))
            .fetchOne()
            ?.into(User::class.java)
    }

    override fun retrieveUsers(): Collection<User> {
        return dsl.selectFrom(USERS)
            .limit(100)
            .fetchArray()
            .map { it.into(User::class.java) }
    }
}