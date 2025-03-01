package com.sljricardo.tkmanager.datasource

import com.sljricardo.tkmanager.model.Task
import com.sljricardo.tkmanager.model.TaskState
import com.sljricardo.tkmanager.model.User

interface TaskDatasource {
    /**
     * Creates a new task.
     *
     * @param task The task to be created.
     * @return The created task.
     */
    fun newTask(task: Task): Task

    /**
     * Searches for tasks that match the given parameter.
     *
     * @param searchParam The search string to filter tasks.
     * @return A collection of tasks matching the search criteria.
     */
    fun searchTask(searchParam: String): Collection<Task>

    /**
     * Retrieves a task by its unique identifier.
     *
     * @param taskId The unique ID of the task.
     * @return The task if found, otherwise `null`.
     */
    fun retrieveTask(taskId: String): Task?

    /**
     * Retrieves all tasks.
     *
     * @return A collection of all tasks.
     */
    fun retrieveTasks(): Collection<Task>

    /**
     * Removes a task by its unique identifier.
     *
     * @param taskId The unique ID of the task to be removed.
     */
    fun removeTask(taskId: String): Unit

    /**
     * Assigns a task to a specific user.
     *
     * @param taskId The unique ID of the task.
     * @param assignee The user to whom the task will be assigned.
     */
    fun assignTask(taskId: String, assignee: User): Unit

    /**
     * Changes the state of a task.
     *
     * @param taskId The unique ID of the task.
     * @param taskState The new state of the task.
     */
    fun changeTaskState(taskId: String, taskState: TaskState): Unit
}