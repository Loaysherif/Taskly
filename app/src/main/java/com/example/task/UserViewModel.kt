package com.example.task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
     private val TaskRepo:TaskRepo
     val taskDataList: LiveData<List<TaskData>>
     init {
          val taskDao = TaskDatabase.getDatabase(application).taskDao()
            TaskRepo = TaskRepo(taskDao)
            taskDataList = TaskRepo.allTasks
     }

     // Add a new

     fun addTaskData(task: TaskData)=viewModelScope.launch {
          TaskRepo.insertTask(task)
     }

     fun updateTaskData(task: TaskData)=viewModelScope.launch {
          TaskRepo.updateTask(task)
     }

     fun deleteTaskData(task: TaskData)=viewModelScope.launch {
          TaskRepo.deleteTask(task)
     }
}



