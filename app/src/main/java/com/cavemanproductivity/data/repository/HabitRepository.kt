package com.cavemanproductivity.data.repository

import com.cavemanproductivity.data.database.dao.HabitDao
import com.cavemanproductivity.data.model.Habit
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitRepository @Inject constructor(
    private val habitDao: HabitDao
) {
    fun getAllActiveHabits(): Flow<List<Habit>> = habitDao.getAllActiveHabits()
    
    fun getAllHabits(): Flow<List<Habit>> = habitDao.getAllHabits()
    
    suspend fun getHabitById(id: String): Habit? = habitDao.getHabitById(id)
    
    fun getHabitsByCategory(category: String): Flow<List<Habit>> = 
        habitDao.getHabitsByCategory(category)
    
    fun getHabitCategories(): Flow<List<String>> = habitDao.getHabitCategories()
    
    suspend fun insertHabit(habit: Habit) = habitDao.insertHabit(habit)
    
    suspend fun updateHabit(habit: Habit) = habitDao.updateHabit(habit)
    
    suspend fun deleteHabit(habit: Habit) = habitDao.deleteHabit(habit)
    
    suspend fun deactivateHabit(id: String) = habitDao.deactivateHabit(id)
    
    suspend fun completeHabit(habitId: String) {
        val habit = habitDao.getHabitById(habitId) ?: return
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        
        // Check if already completed today
        if (habit.lastCompletedDate == today) return
        
        val newStreak = if (isConsecutiveDay(habit.lastCompletedDate, today, habit.frequency)) {
            habit.streak + 1
        } else {
            1 // Reset streak if not consecutive
        }
        
        val newBestStreak = maxOf(habit.bestStreak, newStreak)
        
        habitDao.updateHabitProgress(habitId, newStreak, newBestStreak, today)
    }
    
    private fun isConsecutiveDay(lastDate: String?, currentDate: String, frequency: com.cavemanproductivity.data.model.HabitFrequency): Boolean {
        if (lastDate == null) return false
        
        try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val last = dateFormat.parse(lastDate) ?: return false
            val current = dateFormat.parse(currentDate) ?: return false
            val daysDiff = (current.time - last.time) / (24 * 60 * 60 * 1000)
            
            return when (frequency) {
                com.cavemanproductivity.data.model.HabitFrequency.DAILY -> daysDiff == 1L
                com.cavemanproductivity.data.model.HabitFrequency.WEEKLY -> daysDiff in 6..8
                com.cavemanproductivity.data.model.HabitFrequency.MONTHLY -> daysDiff in 28..32
                com.cavemanproductivity.data.model.HabitFrequency.CUSTOM -> daysDiff == 1L // Simplified for custom
            }
        } catch (e: Exception) {
            return false
        }
    }
    
    suspend fun getActiveHabitCount(): Int = habitDao.getActiveHabitCount()
    
    suspend fun getTotalStreakCount(): Int = habitDao.getTotalStreakCount()
}