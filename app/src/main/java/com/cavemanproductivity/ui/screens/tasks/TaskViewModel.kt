package com.cavemanproductivity.ui.screens.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cavemanproductivity.data.model.Priority
import com.cavemanproductivity.data.model.Task
import com.cavemanproductivity.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TaskUiState(
    val tasks: List<Task> = emptyList(),
    val categories: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val showAddDialog: Boolean = false,
    val editingTask: Task? = null,
    val filterPriority: Priority? = null,
    val filterCategory: String? = null
)

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()
    
    init {
        loadTasks()
    }
    
    private fun loadTasks() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                combine(
                    taskRepository.getActiveTasks(),
                    taskRepository.getTaskCategories()
                ) { tasks, categories ->
                    val filteredTasks = filterTasks(tasks)
                    _uiState.value = TaskUiState(
                        tasks = filteredTasks,
                        categories = categories,
                        isLoading = false,
                        filterPriority = _uiState.value.filterPriority,
                        filterCategory = _uiState.value.filterCategory
                    )
                }.collect()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to load tasks: ${e.message}"
                )
            }
        }
    }
    
    private fun filterTasks(tasks: List<Task>): List<Task> {
        var filtered = tasks
        
        _uiState.value.filterPriority?.let { priority ->
            filtered = filtered.filter { it.priority == priority }
        }
        
        _uiState.value.filterCategory?.let { category ->
            filtered = filtered.filter { it.category == category }
        }
        
        return filtered.sortedWith(
            compareByDescending<Task> { it.priority.level }
                .thenBy { it.dueDate }
                .thenBy { it.createdDate }
        )
    }
    
    fun addTask(
        title: String,
        description: String,
        category: String,
        priority: Priority,
        dueDate: String? = null,
        estimatedMinutes: Int = 0
    ) {
        viewModelScope.launch {
            try {
                val task = Task(
                    title = title,
                    description = description,
                    category = category,
                    priority = priority,
                    dueDate = dueDate,
                    estimatedMinutes = estimatedMinutes
                )
                taskRepository.insertTask(task)
                hideAddDialog()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to add task: ${e.message}"
                )
            }
        }
    }
    
    fun updateTask(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.updateTask(task)
                _uiState.value = _uiState.value.copy(editingTask = null)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to update task: ${e.message}"
                )
            }
        }
    }
    
    fun completeTask(taskId: String) {
        viewModelScope.launch {
            try {
                taskRepository.completeTask(taskId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to complete task: ${e.message}"
                )
            }
        }
    }
    
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            try {
                taskRepository.deleteTask(task)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = "Failed to delete task: ${e.message}"
                )
            }
        }
    }
    
    fun setFilterPriority(priority: Priority?) {
        _uiState.value = _uiState.value.copy(filterPriority = priority)
        loadTasks()
    }
    
    fun setFilterCategory(category: String?) {
        _uiState.value = _uiState.value.copy(filterCategory = category)
        loadTasks()
    }
    
    fun showAddDialog() {
        _uiState.value = _uiState.value.copy(showAddDialog = true)
    }
    
    fun hideAddDialog() {
        _uiState.value = _uiState.value.copy(showAddDialog = false)
    }
    
    fun startEditingTask(task: Task) {
        _uiState.value = _uiState.value.copy(editingTask = task)
    }
    
    fun stopEditingTask() {
        _uiState.value = _uiState.value.copy(editingTask = null)
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}