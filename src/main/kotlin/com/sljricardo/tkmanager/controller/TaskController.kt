package com.sljricardo.tkmanager.controller

import com.sljricardo.tkmanager.dto.AssignTaskRequest
import com.sljricardo.tkmanager.dto.ChangeTaskStateRequest
import com.sljricardo.tkmanager.model.Task
import com.sljricardo.tkmanager.dto.NewTaskRequest
import com.sljricardo.tkmanager.service.TaskService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/task")
class TaskController(private val taskService: TaskService) {
    @GetMapping("/{taskId}")
    fun getTask(@PathVariable taskId: String): Task? = taskService.getTask(taskId)

    @GetMapping("/all", "/search")
    fun getTasks(@RequestParam query: String?): Collection<Task> = taskService.getTasks(query)

    @PostMapping
    fun addTask(@RequestBody task: NewTaskRequest) = taskService.addTask(task)

    @PostMapping("/assign")
    fun assignTask(@RequestBody assignTaskRequest: AssignTaskRequest) {
        taskService.assignTask(assignTaskRequest.taskId, assignTaskRequest.userId)
    }

    @PostMapping("/{taskId}/state")
    fun changeTaskState(@PathVariable taskId: String, @RequestBody request: ChangeTaskStateRequest) {
        taskService.changeTaskSate(taskId, request.state)
    }
}