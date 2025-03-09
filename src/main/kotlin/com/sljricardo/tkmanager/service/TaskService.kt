package com.sljricardo.tkmanager.service

import com.sljricardo.tkmanager.datasource.TaskDatasource
import com.sljricardo.tkmanager.model.Task
import com.sljricardo.tkmanager.dto.NewTaskRequest
import com.sljricardo.tkmanager.model.TaskState
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TaskService(
    val taskDatasource: TaskDatasource,
    val userService: UserService,
    val eventService: EventService
) {
    fun addTask(task: NewTaskRequest): Task {
        val newTask: Task = Task(
            id = UUID.randomUUID().toString(),
            name = task.name,
            description = task.description ?: "",
            state = TaskState.TODO,
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

        eventService.emit(
            event = mapOf(
                "event" to "CHANGE_ASSIGNEE",
                "user" to user,
                "task" to task
            ).toString(),
            userId = user.id,
        )
    }

    fun changeTaskSate(taskId: String, taskState: TaskState) {
        this.getTask(taskId) ?: throw IllegalArgumentException("No task found")

        runCatching { TaskState.valueOf(taskState.name.uppercase()) }
            .getOrElse { throw IllegalArgumentException("Invalid task state: ${taskState.name} options ${TaskState.entries.toTypedArray()}") }

        taskDatasource.changeTaskState(taskId, taskState)
    }
}