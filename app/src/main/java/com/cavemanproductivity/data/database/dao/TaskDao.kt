package com.cavemanproductivity.data.database.dao

import androidx.room.*
import com.cavemanproductivity.data.model.Priority
import com.cavemanproductivity.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM tasks WHERE isCompleted = 0 ORDER BY priority DESC, dueDate ASC")
    fun getActiveTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks ORDER BY createdDate DESC")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: String): Task?

    @Query("SELECT * FROM tasks WHERE category = :category AND isCompleted = 0")
    fun getTasksByCategory(category: String): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE priority = :priority AND isCompleted = 0")
    fun getTasksByPriority(priority: Priority): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE dueDate = :date AND isCompleted = 0")
    fun getTasksByDate(date: String): Flow<List<Task>>

    @Query("SELECT * FROM tasks WHERE dueDate BETWEEN :startDate AND :endDate")
    fun getTasksInDateRange(startDate: String, endDate: String): Flow<List<Task>>

    @Query("SELECT DISTINCT category FROM tasks")
    fun getTaskCategories(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("UPDATE tasks SET isCompleted = :isCompleted, completedDate = :completedDate WHERE id = :id")
    suspend fun updateTaskCompletion(id: String, isCompleted: Boolean, completedDate: String?)

    @Query("SELECT COUNT(*) FROM tasks WHERE isCompleted = 0")
    suspend fun getActiveTaskCount(): Int

    @Query("SELECT COUNT(*) FROM tasks WHERE isCompleted = 1")
    suspend fun getCompletedTaskCount(): Int

    @Query("SELECT COUNT(*) FROM tasks WHERE dueDate = :date AND isCompleted = 0")
    suspend fun getTasksCountForDate(date: String): Int
}