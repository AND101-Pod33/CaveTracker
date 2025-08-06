package com.cavemanproductivity.ui.screens.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cavemanproductivity.data.model.Habit
import com.cavemanproductivity.data.model.Task
import com.cavemanproductivity.data.repository.HabitRepository
import com.cavemanproductivity.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class CalendarDay(
    val date: String,
    val dayOfMonth: Int,
    val isCurrentMonth: Boolean,
    val isToday: Boolean,
    val tasks: List<Task> = emptyList(),
    val completedHabits: List<Habit> = emptyList(),
    val hasEvents: Boolean = false
)

data class CalendarUiState(
    val currentMonth: String = "",
    val currentYear: Int = 0,
    val days: List<CalendarDay> = emptyList(),
    val selectedDate: String? = null,
    val selectedDayTasks: List<Task> = emptyList(),
    val selectedDayHabits: List<Habit> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val habitRepository: HabitRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CalendarUiState())
    val uiState: StateFlow<CalendarUiState> = _uiState.asStateFlow()
    
    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val monthFormat = SimpleDateFormat("MMMM", Locale.getDefault())
    
    init {
        loadCurrentMonth()
    }
    
    fun loadCurrentMonth() {
        val currentMonth = monthFormat.format(calendar.time)
        val currentYear = calendar.get(Calendar.YEAR)
        
        _uiState.value = _uiState.value.copy(
            currentMonth = currentMonth,
            currentYear = currentYear,
            isLoading = true
        )
        
        loadCalendarData()
    }
    
    fun navigateToNextMonth() {
        calendar.add(Calendar.MONTH, 1)
        loadCurrentMonth()
    }
    
    fun navigateToPreviousMonth() {
        calendar.add(Calendar.MONTH, -1)
        loadCurrentMonth()
    }
    
    fun selectDate(date: String) {
        _uiState.value = _uiState.value.copy(selectedDate = date)
        loadSelectedDayData(date)
    }
    
    private fun loadCalendarData() {
        viewModelScope.launch {
            try {
                // Get first day of month and calculate calendar grid
                val monthStart = Calendar.getInstance().apply {
                    set(Calendar.YEAR, calendar.get(Calendar.YEAR))
                    set(Calendar.MONTH, calendar.get(Calendar.MONTH))
                    set(Calendar.DAY_OF_MONTH, 1)
                }
                
                val monthEnd = Calendar.getInstance().apply {
                    set(Calendar.YEAR, calendar.get(Calendar.YEAR))
                    set(Calendar.MONTH, calendar.get(Calendar.MONTH))
                    set(Calendar.DAY_OF_MONTH, getActualMaximum(Calendar.DAY_OF_MONTH))
                }
                
                val startDate = dateFormat.format(monthStart.time)
                val endDate = dateFormat.format(monthEnd.time)
                
                // Load tasks and habits for the month
                combine(
                    taskRepository.getTasksInDateRange(startDate, endDate),
                    habitRepository.getAllActiveHabits()
                ) { tasks, habits ->
                    val days = generateCalendarDays(monthStart, monthEnd, tasks, habits)
                    _uiState.value = _uiState.value.copy(
                        days = days,
                        isLoading = false
                    )
                }.collect()
                
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load calendar: ${e.message}"
                )
            }
        }
    }
    
    private fun generateCalendarDays(
        monthStart: Calendar,
        monthEnd: Calendar,
        tasks: List<Task>,
        habits: List<Habit>
    ): List<CalendarDay> {
        val days = mutableListOf<CalendarDay>()
        val today = dateFormat.format(Date())
        
        // Calculate start of calendar grid (include previous month days)
        val calendarStart = Calendar.getInstance().apply {
            time = monthStart.time
            set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        }
        
        // Generate 42 days (6 weeks)
        val currentDate = Calendar.getInstance().apply { time = calendarStart.time }
        
        repeat(42) {
            val dateString = dateFormat.format(currentDate.time)
            val dayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH)
            val isCurrentMonth = currentDate.get(Calendar.MONTH) == monthStart.get(Calendar.MONTH)
            val isToday = dateString == today
            
            // Filter tasks for this day
            val dayTasks = tasks.filter { task ->
                task.dueDate == dateString || task.completedDate == dateString
            }
            
            // Check for completed habits on this day
            val dayHabits = habits.filter { habit ->
                habit.lastCompletedDate == dateString
            }
            
            days.add(
                CalendarDay(
                    date = dateString,
                    dayOfMonth = dayOfMonth,
                    isCurrentMonth = isCurrentMonth,
                    isToday = isToday,
                    tasks = dayTasks,
                    completedHabits = dayHabits,
                    hasEvents = dayTasks.isNotEmpty() || dayHabits.isNotEmpty()
                )
            )
            
            currentDate.add(Calendar.DAY_OF_MONTH, 1)
        }
        
        return days
    }
    
    private fun loadSelectedDayData(date: String) {
        viewModelScope.launch {
            try {
                combine(
                    taskRepository.getTasksByDate(date),
                    habitRepository.getAllActiveHabits()
                ) { tasks, habits ->
                    val dayHabits = habits.filter { it.lastCompletedDate == date }
                    _uiState.value = _uiState.value.copy(
                        selectedDayTasks = tasks,
                        selectedDayHabits = dayHabits
                    )
                }.collect()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to load day data: ${e.message}"
                )
            }
        }
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}