package com.cavemanproductivity.ui.screens.focus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FocusUiState(
    val mode: TimerMode = TimerMode.POMODORO,
    val state: TimerState = TimerState.STOPPED,
    val currentTime: Long = 25 * 60, // 25 minutes in seconds
    val totalTime: Long = 25 * 60,
    val pomodoroPhase: PomodoroPhase = PomodoroPhase.WORK,
    val completedPomodoros: Int = 0,
    val workDuration: Int = 25, // minutes
    val shortBreakDuration: Int = 5, // minutes
    val longBreakDuration: Int = 15, // minutes
    val longBreakInterval: Int = 4 // every 4 pomodoros
)

@HiltViewModel
class FocusTimerViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(FocusUiState())
    val uiState: StateFlow<FocusUiState> = _uiState.asStateFlow()
    
    private var timerJob: Job? = null
    
    fun startTimer() {
        if (_uiState.value.state == TimerState.RUNNING) return
        
        _uiState.value = _uiState.value.copy(state = TimerState.RUNNING)
        
        timerJob = viewModelScope.launch {
            while (_uiState.value.state == TimerState.RUNNING) {
                delay(1000)
                if (_uiState.value.mode == TimerMode.POMODORO) {
                    if (_uiState.value.currentTime > 0) {
                        decrementPomodoroTime()
                    } else {
                        onPomodoroComplete()
                        break
                    }
                } else {
                    incrementStopwatchTime()
                }
            }
        }
    }
    
    fun pauseTimer() {
        _uiState.value = _uiState.value.copy(state = TimerState.PAUSED)
        timerJob?.cancel()
    }
    
    fun stopTimer() {
        _uiState.value = _uiState.value.copy(
            state = TimerState.STOPPED,
            currentTime = if (_uiState.value.mode == TimerMode.STOPWATCH) 0 else _uiState.value.totalTime
        )
        timerJob?.cancel()
    }
    
    fun resetTimer() {
        timerJob?.cancel()
        _uiState.value = _uiState.value.copy(
            state = TimerState.STOPPED,
            currentTime = if (_uiState.value.mode == TimerMode.STOPWATCH) 0 else _uiState.value.totalTime
        )
    }
    
    fun switchMode(mode: TimerMode) {
        timerJob?.cancel()
        _uiState.value = _uiState.value.copy(
            mode = mode,
            state = TimerState.STOPPED,
            currentTime = if (mode == TimerMode.STOPWATCH) 0 else _uiState.value.workDuration * 60L
        )
    }
    
    fun updateWorkDuration(minutes: Int) {
        val newDuration = minutes.coerceIn(1, 60)
        _uiState.value = _uiState.value.copy(
            workDuration = newDuration,
            totalTime = newDuration * 60L,
            currentTime = if (_uiState.value.state == TimerState.STOPPED) newDuration * 60L else _uiState.value.currentTime
        )
    }
    
    fun updateShortBreakDuration(minutes: Int) {
        _uiState.value = _uiState.value.copy(shortBreakDuration = minutes.coerceIn(1, 30))
    }
    
    fun updateLongBreakDuration(minutes: Int) {
        _uiState.value = _uiState.value.copy(longBreakDuration = minutes.coerceIn(1, 60))
    }
    
    private fun decrementPomodoroTime() {
        val newTime = _uiState.value.currentTime - 1
        _uiState.value = _uiState.value.copy(currentTime = newTime)
    }
    
    private fun incrementStopwatchTime() {
        val newTime = _uiState.value.currentTime + 1
        _uiState.value = _uiState.value.copy(currentTime = newTime)
    }
    
    private fun onPomodoroComplete() {
        val currentState = _uiState.value
        
        when (currentState.pomodoroPhase) {
            PomodoroPhase.WORK -> {
                val completedPomodoros = currentState.completedPomodoros + 1
                val isLongBreak = completedPomodoros % currentState.longBreakInterval == 0
                
                val nextPhase = if (isLongBreak) PomodoroPhase.LONG_BREAK else PomodoroPhase.SHORT_BREAK
                val nextDuration = if (isLongBreak) currentState.longBreakDuration else currentState.shortBreakDuration
                
                _uiState.value = currentState.copy(
                    state = TimerState.STOPPED,
                    pomodoroPhase = nextPhase,
                    completedPomodoros = completedPomodoros,
                    currentTime = nextDuration * 60L,
                    totalTime = nextDuration * 60L
                )
            }
            PomodoroPhase.SHORT_BREAK, PomodoroPhase.LONG_BREAK -> {
                _uiState.value = currentState.copy(
                    state = TimerState.STOPPED,
                    pomodoroPhase = PomodoroPhase.WORK,
                    currentTime = currentState.workDuration * 60L,
                    totalTime = currentState.workDuration * 60L
                )
            }
        }
    }
    
    fun getProgressPercentage(): Float {
        return if (_uiState.value.totalTime > 0) {
            if (_uiState.value.mode == TimerMode.STOPWATCH) {
                // For stopwatch, we don't have a total time, so show based on elapsed time
                1f - (_uiState.value.currentTime / (60f * 60f)) // Show progress up to 1 hour
            } else {
                (_uiState.value.totalTime - _uiState.value.currentTime) / _uiState.value.totalTime.toFloat()
            }
        } else 0f
    }
    
    fun formatTime(seconds: Long): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        
        return if (hours > 0) {
            String.format("%02d:%02d:%02d", hours, minutes, secs)
        } else {
            String.format("%02d:%02d", minutes, secs)
        }
    }
}