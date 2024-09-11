package com.example.task

import androidx.lifecycle.LiveData

class TaskRepo(private val taskDao: TaskDao) {

    val allTasks: LiveData<List<TaskData>> = taskDao.getAllTasks()

    suspend fun insertTask(task: TaskData) {
        taskDao.insert(task)
    }

    suspend fun updateTask(task: TaskData) {
        taskDao.update(task)
    }

    suspend fun deleteTask(task: TaskData) {
        taskDao.delete(task)
    }
}
