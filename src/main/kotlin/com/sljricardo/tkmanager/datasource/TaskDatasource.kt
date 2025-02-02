package com.sljricardo.tkmanager.datasource

import com.sljricardo.tkmanager.model.Task
import com.sljricardo.tkmanager.model.User

interface TaskDatasource {
    fun newTask(task: Task): Task
    fun searchTask(searchParam: String): Collection<Task>
    fun retrieveTask(taskId: String): Task?
    fun retrieveTasks(): Collection<Task>
    fun removeTask(taskId: String): Unit
    fun assignTask(taskId: String, assignee: User): Unit
}