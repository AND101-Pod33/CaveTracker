package com.cavemanproductivity.ui.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cavemanproductivity.data.repository.HabitRepository
import com.cavemanproductivity.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StatsUiState(
    val totalHabits: Int = 0,
    val completedToday: Int = 0,
    val successRate: Int = 0,
    val activeHabits: Int = 0,
    val longestStreak: Int = 0,
    val weeklyAverage: Int = 0,
    val totalTasks: Int = 0,
    val completedTasks: Int = 0,
    val taskCompletionRate: Int = 0,
    val totalFocusSessions: Int = 0,
    val totalFocusTime: Int = 0,
    val avgFocusSession: Int = 0,
    val weeklyProgress: Int = 0,
    val lastWeekProgress: Int = 0,
    val improvement: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val habitRepository: HabitRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(StatsUiState(isLoading = true))
    val uiState: StateFlow<StatsUiState> = _uiState.asStateFlow()
    
    init {
        loadStats()
    }
    
    private fun loadStats() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                
                // Load habits data
                val habits = habitRepository.getAllHabits()
                val totalHabits = habits.size
                val activeHabits = habits.count { it.isActive }
                
                // Calculate completed today (mock data for now)
                val completedToday = habits.count { it.isActive } / 2 // Mock calculation
                
                // Calculate success rate
                val successRate = if (totalHabits > 0) {
                    (completedToday * 100) / totalHabits
                } else 0
                
                // Calculate longest streak (mock data)
                val longestStreak = habits.maxOfOrNull { it.currentStreak } ?: 0
                
                // Calculate weekly average (mock data)
                val weeklyAverage = 75 // Mock data
                
                // Load tasks data
                val tasks = taskRepository.getAllTasks()
                val totalTasks = tasks.size
                val completedTasks = tasks.count { it.isCompleted }
                
                // Calculate task completion rate
                val taskCompletionRate = if (totalTasks > 0) {
                    (completedTasks * 100) / totalTasks
                } else 0
                
                // Focus session stats (mock data)
                val totalFocusSessions = 12
                val totalFocusTime = 180 // minutes
                val avgFocusSession = if (totalFocusSessions > 0) {
                    totalFocusTime / totalFocusSessions
                } else 0
                
                // Weekly progress (mock data)
                val weeklyProgress = 85
                val lastWeekProgress = 72
                val improvement = weeklyProgress - lastWeekProgress
                
                _uiState.value = StatsUiState(
                    totalHabits = totalHabits,
                    completedToday = completedToday,
                    successRate = successRate,
                    activeHabits = activeHabits,
                    longestStreak = longestStreak,
                    weeklyAverage = weeklyAverage,
                    totalTasks = totalTasks,
                    completedTasks = completedTasks,
                    taskCompletionRate = taskCompletionRate,
                    totalFocusSessions = totalFocusSessions,
                    totalFocusTime = totalFocusTime,
                    avgFocusSession = avgFocusSession,
                    weeklyProgress = weeklyProgress,
                    lastWeekProgress = lastWeekProgress,
                    improvement = improvement,
                    isLoading = false
                )
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load stats"
                )
            }
        }
    }
    
    fun refreshStats() {
        loadStats()
    }
} 