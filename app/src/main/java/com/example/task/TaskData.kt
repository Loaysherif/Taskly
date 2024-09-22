package com.example.task

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "task_table")
data class TaskData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val task: String,
) : Serializable
