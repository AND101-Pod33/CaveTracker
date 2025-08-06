package com.cavemanproductivity.ui.screens.habits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cavemanproductivity.data.model.Habit
import com.cavemanproductivity.data.model.HabitFrequency
import com.cavemanproductivity.data.repository.HabitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class HabitUiState(
    val habits: List<Habit> = emptyList(),
    val categories: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val showAddDialog: Boolean = false,
    val editingHabit: Habit? = null
)

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val habitRepository: HabitRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(HabitUiState())
    val uiState: StateFlow<HabitUiState> = _uiState.asStateFlow()
    
    init {
        loadHabits()
    }
    
    private fun loadHabits() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                combine(
                    habitRepository.getAllActiveHabits(),
                    habitRepository.getHabitCategories()
                ) { habits, categories ->
                    _uiState.value = HabitUiState(
                        habits = habits,
                        categories = categories,
                        isLoading = false
                    )
                }.collect()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load habits: ${e.message}"
                )
            }
        }
    }
    
    fun addHabit(
        name: String,
        description: String,
        frequency: HabitFrequency,
        category: String,
        customInterval: Int = 0
    ) {
        viewModelScope.launch {
            try {
                val habit = Habit(
                    name = name,
                    description = description,
                    frequency = frequency,
                    category = category,
                    customInterval = customInterval
                )
                habitRepository.insertHabit(habit)
                hideAddDialog()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to add habit: ${e.message}"
                )
            }
        }
    }
    
    fun updateHabit(habit: Habit) {
        viewModelScope.launch {
            try {
                habitRepository.updateHabit(habit)
                _uiState.value = _uiState.value.copy(editingHabit = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to update habit: ${e.message}"
                )
            }
        }
    }
    
    fun completeHabit(habitId: String) {
        viewModelScope.launch {
            try {
                habitRepository.completeHabit(habitId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to complete habit: ${e.message}"
                )
            }
        }
    }
    
    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            try {
                habitRepository.deleteHabit(habit)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to delete habit: ${e.message}"
                )
            }
        }
    }
    
    fun showAddDialog() {
        _uiState.value = _uiState.value.copy(showAddDialog = true)
    }
    
    fun hideAddDialog() {
        _uiState.value = _uiState.value.copy(showAddDialog = false)
    }
    
    fun startEditingHabit(habit: Habit) {
        _uiState.value = _uiState.value.copy(editingHabit = habit)
    }
    
    fun stopEditingHabit() {
        _uiState.value = _uiState.value.copy(editingHabit = null)
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    fun isHabitCompletedToday(habit: Habit): Boolean {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return habit.lastCompletedDate == today
    }
}