package com.cavemanproductivity.ui.screens.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cavemanproductivity.data.model.DailyQuote
import com.cavemanproductivity.data.repository.HabitRepository
import com.cavemanproductivity.data.repository.QuoteRepository
import com.cavemanproductivity.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardStats(
    val activeHabits: Int = 0,
    val totalStreaks: Int = 0,
    val activeTasks: Int = 0,
    val completedTasks: Int = 0
)

data class DashboardUiState(
    val stats: DashboardStats = DashboardStats(),
    val dailyQuote: DailyQuote? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val habitRepository: HabitRepository,
    private val taskRepository: TaskRepository,
    private val quoteRepository: QuoteRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()
    
    init {
        loadDashboardData()
    }
    
    private fun loadDashboardData() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                
                // Load stats
                val activeHabits = habitRepository.getActiveHabitCount()
                val totalStreaks = habitRepository.getTotalStreakCount()
                val activeTasks = taskRepository.getActiveTaskCount()
                val completedTasks = taskRepository.getCompletedTaskCount()
                
                val stats = DashboardStats(
                    activeHabits = activeHabits,
                    totalStreaks = totalStreaks,
                    activeTasks = activeTasks,
                    completedTasks = completedTasks
                )
                
                // Load daily quote
                val dailyQuote = quoteRepository.getTodaysQuote()
                
                _uiState.value = DashboardUiState(
                    stats = stats,
                    dailyQuote = dailyQuote,
                    isLoading = false
                )
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load dashboard: ${e.message}"
                )
            }
        }
    }
    
    fun refreshDashboard() {
        loadDashboardData()
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}