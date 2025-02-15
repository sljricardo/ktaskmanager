package com.sljricardo.tkmanager.datasource.sqlite

import org.jooq.Record
import com.sljricardo.jooq.tables.references.TASK
import com.sljricardo.jooq.tables.references.USERS
import com.sljricardo.tkmanager.datasource.TaskDatasource
import com.sljricardo.tkmanager.model.Task
import com.sljricardo.tkmanager.model.User
import org.jooq.DSLContext
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("sqlite")
class SqliteTaskDatasource(private val dsl: DSLContext): TaskDatasource {
    fun mapTask(record: Record): Task {
        return Task(
            id = record.getValue(TASK.ID)!!,
            name = record.getValue(TASK.NAME)!!,
            description = record.getValue(TASK.DESCRIPTION),
            assignee = record.getValue(TASK.ASSIGNEE_ID)?.let {
                record[USERS.ID]?.let { userId ->
                    User(
                        id = userId,
                        name = record[USERS.NAME] ?: "Unknown"
                    )
                }
            }
        )
    }

    override fun newTask(task: Task): Task {
        dsl.insertInto(TASK)
            .set(TASK.ID, task.id)
            .set(TASK.NAME, task.name)
            .set(TASK.DESCRIPTION, task.description)
            .set(TASK.ASSIGNEE_ID, task.assignee?.id)
            .execute()

        return task
    }

    override fun retrieveTask(taskId: String): Task? {
        return dsl.select(
            TASK.ID, TASK.NAME, TASK.DESCRIPTION, TASK.ASSIGNEE_ID, // Task table
            USERS.ID, USERS.NAME // Users table
        ).from(TASK).leftJoin(USERS).on(TASK.ASSIGNEE_ID.eq(USERS.ID))
            .where(TASK.ID.eq(taskId))
            .fetchOne(::mapTask)
    }

    override fun retrieveTasks(): Collection<Task> {
        return dsl.selectFrom(TASK)
            .limit(100)
            .fetchArray()
            .map(::mapTask)
    }

    override fun searchTask(searchParam: String): Collection<Task> {
        return dsl.selectFrom(TASK)
            .where(TASK.NAME.likeIgnoreCase("%${searchParam}%"))
            .fetch(::mapTask)
    }

    override fun removeTask(taskId: String) {
        dsl.deleteFrom(TASK)
            .where(TASK.ID.eq(taskId))
    }

    override fun assignTask(taskId: String, assignee: User) {
        val rowsUpdated = dsl.update(TASK)
            .set(TASK.ASSIGNEE_ID, assignee.id)
            .where(TASK.ID.eq(taskId))
            .execute()

        println(rowsUpdated)

        if (rowsUpdated == 0) {
            throw IllegalArgumentException("Task with ID $taskId not found.")
        }
    }
}