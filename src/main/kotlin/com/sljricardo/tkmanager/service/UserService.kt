package com.sljricardo.tkmanager.service

import com.sljricardo.tkmanager.datasource.UserDatasource
import com.sljricardo.tkmanager.model.User
import org.springframework.stereotype.Service

@Service
class UserService(val userDatasource: UserDatasource) {
    fun addUser(user: User): User = userDatasource.newUser(user)
    fun getUser(userId: String): User? = userDatasource.retrieveUser(userId)
}