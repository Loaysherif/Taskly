package com.example.task

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel : ViewModel() {
     private val _taskDataList = MutableLiveData<MutableList<TaskData>>()
     val taskDataList: LiveData<MutableList<TaskData>> = _taskDataList

     init {
          initializeData()
     }

     // Add a new task
     fun addTaskData(task: TaskData) {
          _taskDataList.value = _taskDataList.value?.apply { add(task) } ?: mutableListOf(task)
     }

     // Update an existing task at the specified position
     fun updateTaskData(task: TaskData, position: Int) {
          _taskDataList.value = _taskDataList.value?.apply {
               if (position in indices) {
                    this[position] = task
               }
          }
     }

     // Initialize with some sample data
     private fun initializeData() {
          _taskDataList.value = mutableListOf(
               TaskData("Finish Project" , 1),
               TaskData("Fly a Plane" , 2),
               TaskData("Learn Kotlin" , 0)
          )
     }

     // Delete a task
     fun deleteTaskData(task: TaskData) {
          _taskDataList.value = _taskDataList.value?.apply { remove(task) }
     }
}
