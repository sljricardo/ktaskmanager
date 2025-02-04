package com.sljricardo.tkmanager.service

import com.sljricardo.tkmanager.datasource.TaskDatasource
import com.sljricardo.tkmanager.model.Task
import com.sljricardo.tkmanager.dto.NewTaskRequest
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TaskService(val taskDatasource: TaskDatasource, val userService: UserService) {
    fun addTask(task: NewTaskRequest): Task {
        val newTask: Task = Task(
            id = UUID.randomUUID().toString(),
            name = task.name,
            description = task.description ?: "",
            assignee = null
        )
        taskDatasource.newTask(newTask)
        return newTask
    }

    fun getTask(taskId: String): Task? {
        return taskDatasource.retrieveTask(taskId)
    }

    fun getTasks(searchParam: String?): Collection<Task> {
        if (searchParam.isNullOrEmpty()) {
            return taskDatasource.retrieveTasks()
        }
        return taskDatasource.searchTask(searchParam)
    }

    fun assignTask(taskId: String, userId: String) {
        val task = this.getTask(taskId) ?: throw IllegalArgumentException("No task found")
        val user = userService.getUser(userId) ?: throw IllegalArgumentException("User with ID $userId does not exist.")

        taskDatasource.assignTask(task.id, user)
    }
}