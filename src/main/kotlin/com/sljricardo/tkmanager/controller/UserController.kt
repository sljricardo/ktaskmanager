package com.sljricardo.tkmanager.controller

import com.sljricardo.tkmanager.model.User
import com.sljricardo.tkmanager.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(val userService: UserService) {
    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: String): User? = userService.getUser(userId)

    @PostMapping()
    fun addUser(@RequestBody user: User): User = userService.addUser(user)
}