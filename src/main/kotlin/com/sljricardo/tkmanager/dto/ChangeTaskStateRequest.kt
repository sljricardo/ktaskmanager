package com.sljricardo.tkmanager.dto

import com.sljricardo.tkmanager.model.TaskState

data class ChangeTaskStateRequest (
    val state: TaskState
)