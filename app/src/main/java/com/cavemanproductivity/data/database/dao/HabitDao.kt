package com.cavemanproductivity.data.database.dao

import androidx.room.*
import com.cavemanproductivity.data.model.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits WHERE isActive = 1 ORDER BY createdDate DESC")
    fun getAllActiveHabits(): Flow<List<Habit>>

    @Query("SELECT * FROM habits ORDER BY createdDate DESC")
    fun getAllHabits(): Flow<List<Habit>>

    @Query("SELECT * FROM habits WHERE id = :id")
    suspend fun getHabitById(id: String): Habit?

    @Query("SELECT * FROM habits WHERE category = :category AND isActive = 1")
    fun getHabitsByCategory(category: String): Flow<List<Habit>>

    @Query("SELECT DISTINCT category FROM habits WHERE isActive = 1")
    fun getHabitCategories(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("UPDATE habits SET isActive = 0 WHERE id = :id")
    suspend fun deactivateHabit(id: String)

    @Query("UPDATE habits SET streak = :streak, bestStreak = :bestStreak, lastCompletedDate = :date WHERE id = :id")
    suspend fun updateHabitProgress(id: String, streak: Int, bestStreak: Int, date: String)

    @Query("SELECT COUNT(*) FROM habits WHERE isActive = 1")
    suspend fun getActiveHabitCount(): Int

    @Query("SELECT SUM(streak) FROM habits WHERE isActive = 1")
    suspend fun getTotalStreakCount(): Int
}