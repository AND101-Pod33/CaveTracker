package com.cavemanproductivity.ui.screens.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class StatsViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(StatsUiState(isLoading = true))
    val uiState: StateFlow<StatsUiState> = _uiState.asStateFlow()
    
    init {
        loadStats()
    }
    
    private fun loadStats() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                
                // Simulate loading delay
                kotlinx.coroutines.delay(500)
                
                // Mock data for now to prevent crashes
                val totalHabits = 8
                val activeHabits = 6
                val completedToday = 4
                val successRate = 75
                val longestStreak = 12
                val weeklyAverage = 80
                
                val totalTasks = 15
                val completedTasks = 12
                val taskCompletionRate = 80
                
                val totalFocusSessions = 12
                val totalFocusTime = 180 // minutes
                val avgFocusSession = 15
                
                val weeklyProgress = 85
                val lastWeekProgress = 72
                val improvement = 13
                
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