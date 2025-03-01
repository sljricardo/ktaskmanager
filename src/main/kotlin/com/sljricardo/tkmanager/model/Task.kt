package com.sljricardo.tkmanager.model

enum class TaskState {
    TODO, DOING, DONE
}

data class Task (
    val id: String,
    val name: String,
    val description: String?,
    var state: TaskState,
    var assignee: User?,
)

