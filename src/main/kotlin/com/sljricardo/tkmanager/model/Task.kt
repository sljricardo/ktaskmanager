package com.sljricardo.tkmanager.model

data class Task (
    val id: String,
    val name: String,
    val description: String?,
    var assignee: User?,
)

