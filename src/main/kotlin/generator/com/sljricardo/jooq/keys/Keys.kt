/*
 * This file is generated by jOOQ.
 */
package com.sljricardo.jooq.keys


import com.sljricardo.jooq.tables.Task
import com.sljricardo.jooq.tables.Users
import com.sljricardo.jooq.tables.records.TaskRecord
import com.sljricardo.jooq.tables.records.UsersRecord

import org.jooq.ForeignKey
import org.jooq.UniqueKey
import org.jooq.impl.DSL
import org.jooq.impl.Internal



// -------------------------------------------------------------------------
// UNIQUE and PRIMARY KEY definitions
// -------------------------------------------------------------------------

val TASK__PK_TASK: UniqueKey<TaskRecord> = Internal.createUniqueKey(Task.TASK, DSL.name("pk_TASK"), arrayOf(Task.TASK.ID), true)
val USERS__PK_USERS: UniqueKey<UsersRecord> = Internal.createUniqueKey(Users.USERS, DSL.name("pk_USERS"), arrayOf(Users.USERS.ID), true)

// -------------------------------------------------------------------------
// FOREIGN KEY definitions
// -------------------------------------------------------------------------

val TASK__FK_TASK_PK_USERS: ForeignKey<TaskRecord, UsersRecord> = Internal.createForeignKey(Task.TASK, DSL.name("fk_TASK_pk_USERS"), arrayOf(Task.TASK.ASSIGNEE_ID), com.sljricardo.jooq.keys.USERS__PK_USERS, arrayOf(Users.USERS.ID), true)
