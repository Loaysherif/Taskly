package com.example.task

import java.io.Serializable

data class TaskData(
    val task: String,
    val priority: Int
): Serializable {
}
