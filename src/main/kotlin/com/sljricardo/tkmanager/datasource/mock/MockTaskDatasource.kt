package com.sljricardo.tkmanager.datasource.mock

import com.sljricardo.tkmanager.datasource.TaskDatasource
import com.sljricardo.tkmanager.model.Task
import com.sljricardo.tkmanager.model.User
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("local")
class MockTaskDatasource: TaskDatasource {
    private val tasks: MutableCollection<Task> = mutableListOf()

    override fun newTask(task: Task): Task {
        tasks.add(task)
        return task
    }

    override fun retrieveTask(taskId: String): Task? {
        return tasks.firstOrNull { taskId == it.id }
    }

    override fun retrieveTasks(): Collection<Task> = tasks

    override fun searchTask(searchParam: String): Collection<Task> {
        return tasks.filter { it.name.lowercase().contains(searchParam.lowercase()) }
    }

    override fun removeTask(taskId: String) {
        tasks.removeIf { taskId == it.id }
    }

    override fun assignTask(taskId: String, assignee: User) {
        val task = tasks.firstOrNull { taskId == it.id }

        if (task != null) {
            task.assignee = assignee
        }
    }
}