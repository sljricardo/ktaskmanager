package com.sljricardo.tkmanager.datasource

import com.sljricardo.tkmanager.model.User

interface UserDatasource {
    fun newUser(user: User): User
    fun retrieveUser(userId: String): User?
}