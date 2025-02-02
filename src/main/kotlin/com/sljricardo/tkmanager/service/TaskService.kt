package com.sljricardo.tkmanager.service

import com.sljricardo.tkmanager.datasource.TaskDatasource
import com.sljricardo.tkmanager.model.Task
import com.sljricardo.tkmanager.model.TaskRequestBody
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class TaskService(val taskDatasource: TaskDatasource) {
    fun addTask(task: TaskRequestBody): Task {
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
}