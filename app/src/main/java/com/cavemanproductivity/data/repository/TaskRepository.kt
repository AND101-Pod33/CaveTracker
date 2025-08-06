package com.cavemanproductivity.data.repository

import com.cavemanproductivity.data.database.dao.TaskDao
import com.cavemanproductivity.data.model.Priority
import com.cavemanproductivity.data.model.Task
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    fun getActiveTasks(): Flow<List<Task>> = taskDao.getActiveTasks()
    
    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()
    
    suspend fun getTaskById(id: String): Task? = taskDao.getTaskById(id)
    
    fun getTasksByCategory(category: String): Flow<List<Task>> = 
        taskDao.getTasksByCategory(category)
    
    fun getTasksByPriority(priority: Priority): Flow<List<Task>> = 
        taskDao.getTasksByPriority(priority)
    
    fun getTasksByDate(date: String): Flow<List<Task>> = 
        taskDao.getTasksByDate(date)
    
    fun getTasksInDateRange(startDate: String, endDate: String): Flow<List<Task>> = 
        taskDao.getTasksInDateRange(startDate, endDate)
    
    fun getTaskCategories(): Flow<List<String>> = taskDao.getTaskCategories()
    
    suspend fun insertTask(task: Task) = taskDao.insertTask(task)
    
    suspend fun updateTask(task: Task) = taskDao.updateTask(task)
    
    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)
    
    suspend fun completeTask(taskId: String) {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        taskDao.updateTaskCompletion(taskId, true, today)
    }
    
    suspend fun uncompleteTask(taskId: String) {
        taskDao.updateTaskCompletion(taskId, false, null)
    }
    
    suspend fun getActiveTaskCount(): Int = taskDao.getActiveTaskCount()
    
    suspend fun getCompletedTaskCount(): Int = taskDao.getCompletedTaskCount()
    
    suspend fun getTasksCountForDate(date: String): Int = taskDao.getTasksCountForDate(date)
    
    fun getTodaysTasks(): Flow<List<Task>> {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return getTasksByDate(today)
    }
    
    fun getOverdueTasks(): Flow<List<Task>> {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return taskDao.getTasksInDateRange("2000-01-01", today)
    }
}