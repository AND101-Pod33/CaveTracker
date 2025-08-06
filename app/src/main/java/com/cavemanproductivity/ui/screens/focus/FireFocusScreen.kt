package com.cavemanproductivity.ui.screens.focus

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cavemanproductivity.ui.components.StoneButton
import com.cavemanproductivity.ui.components.StoneTablet
import com.cavemanproductivity.ui.theme.*
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FireFocusScreen(
    navController: NavController,
    viewModel: FocusTimerViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Text(
            text = "🔥 BATTLE RAGE",
            style = MaterialTheme.typography.displayMedium,
            color = SaddleBrown,
            textAlign = TextAlign.Center
        )
        
        // Mode Selection
        ModeSelector(
            currentMode = uiState.mode,
            onModeChange = { viewModel.switchMode(it) }
        )
        
        // Animated Fire Timer
        AnimatedFireTimer(
            progress = viewModel.getProgressPercentage(),
            isRunning = uiState.state == TimerState.RUNNING,
            phase = uiState.pomodoroPhase,
            mode = uiState.mode
        )
        
        // Time Display
        StoneTablet {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = viewModel.formatTime(uiState.currentTime),
                    style = MaterialTheme.typography.displayLarge,
                    color = when (uiState.pomodoroPhase) {
                        PomodoroPhase.WORK -> Tomato
                        PomodoroPhase.SHORT_BREAK -> DarkGoldenrod
                        PomodoroPhase.LONG_BREAK -> OliveDrab
                    },
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                
                Text(
                    text = when (uiState.mode) {
                        TimerMode.POMODORO -> when (uiState.pomodoroPhase) {
                            PomodoroPhase.WORK -> "🎯 Focus Time"
                            PomodoroPhase.SHORT_BREAK -> "☕ Short Break"
                            PomodoroPhase.LONG_BREAK -> "🛌 Long Break"
                        }
                        TimerMode.STOPWATCH -> "⏱️ Stopwatch"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    color = DarkSlateGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                
                if (uiState.mode == TimerMode.POMODORO) {
                    Text(
                        text = "🍅 Completed: ${uiState.completedPomodoros}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Chocolate,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
        
        // Timer Controls
        TimerControls(
            state = uiState.state,
            onStart = { viewModel.startTimer() },
            onPause = { viewModel.pauseTimer() },
            onStop = { viewModel.stopTimer() },
            onReset = { viewModel.resetTimer() }
        )
        
        // Settings
        if (uiState.mode == TimerMode.POMODORO) {
            PomodoroSettings(
                workDuration = uiState.workDuration,
                shortBreakDuration = uiState.shortBreakDuration,
                longBreakDuration = uiState.longBreakDuration,
                onWorkDurationChange = { viewModel.updateWorkDuration(it) },
                onShortBreakDurationChange = { viewModel.updateShortBreakDuration(it) },
                onLongBreakDurationChange = { viewModel.updateLongBreakDuration(it) }
            )
        }
    }
}

@Composable
fun ModeSelector(
    currentMode: TimerMode,
    onModeChange: (TimerMode) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StoneButton(
            onClick = { onModeChange(TimerMode.POMODORO) },
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = if (currentMode == TimerMode.POMODORO) "🍅 Pomodoro" else "Pomodoro",
                color = if (currentMode == TimerMode.POMODORO) Tomato else DarkSlateGray
            )
        }
        
        StoneButton(
            onClick = { onModeChange(TimerMode.STOPWATCH) },
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = if (currentMode == TimerMode.STOPWATCH) "⏱️ Stopwatch" else "Stopwatch",
                color = if (currentMode == TimerMode.STOPWATCH) Tomato else DarkSlateGray
            )
        }
    }
}

@Composable
fun AnimatedFireTimer(
    progress: Float,
    isRunning: Boolean,
    phase: PomodoroPhase,
    mode: TimerMode
) {
    val infiniteTransition = rememberInfiniteTransition(label = "fire_animation")
    
    val flameScale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flame_scale"
    )
    
    val flameOpacity by infiniteTransition.animateFloat(
        initialValue = 0.7f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "flame_opacity"
    )
    
    val fireColors = when (phase) {
        PomodoroPhase.WORK -> listOf(FireRed, FireOrange, FireYellow)
        PomodoroPhase.SHORT_BREAK -> listOf(DarkGoldenrod, SandyBrown, Wheat)
        PomodoroPhase.LONG_BREAK -> listOf(OliveDrab, SandyBrown, Wheat)
    }
    
    Box(
        modifier = Modifier.size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        // Progress Circle Background
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = BurlyWood,
                radius = size.minDimension / 2 - 20f,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 8.dp.toPx())
            )
        }
        
        // Progress Circle
        Canvas(modifier = Modifier.fillMaxSize()) {
            val sweepAngle = progress * 360f
            drawArc(
                brush = Brush.sweepGradient(fireColors),
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 8.dp.toPx())
            )
        }
        
        // Animated Fire
        Canvas(
            modifier = Modifier
                .size(120.dp)
                .scale(if (isRunning) flameScale else 0.8f)
        ) {
            drawAnimatedFire(fireColors, if (isRunning) flameOpacity else 0.5f)
        }
    }
}

@Composable
fun TimerControls(
    state: TimerState,
    onStart: () -> Unit,
    onPause: () -> Unit,
    onStop: () -> Unit,
    onReset: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (state) {
            TimerState.STOPPED -> {
                StoneButton(onClick = onStart) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Start")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Start Hunt")
                }
            }
            TimerState.RUNNING -> {
                StoneButton(onClick = onPause) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Pause")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Rest")
                }
            }
            TimerState.PAUSED -> {
                StoneButton(onClick = onStart) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Resume")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Continue")
                }
            }
        }
        
        if (state != TimerState.STOPPED) {
            StoneButton(onClick = onStop) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Stop")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Stop")
            }
        }
        
        StoneButton(onClick = onReset) {
            Text("Reset")
        }
    }
}

@Composable
fun PomodoroSettings(
    workDuration: Int,
    shortBreakDuration: Int,
    longBreakDuration: Int,
    onWorkDurationChange: (Int) -> Unit,
    onShortBreakDurationChange: (Int) -> Unit,
    onLongBreakDurationChange: (Int) -> Unit
) {
    StoneTablet {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "⚙️ Fire Settings",
                style = MaterialTheme.typography.titleLarge,
                color = Chocolate,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            
            SettingSlider(
                label = "Work Time",
                value = workDuration,
                range = 1..60,
                unit = "min",
                onValueChange = onWorkDurationChange
            )
            
            SettingSlider(
                label = "Short Break",
                value = shortBreakDuration,
                range = 1..30,
                unit = "min",
                onValueChange = onShortBreakDurationChange
            )
            
            SettingSlider(
                label = "Long Break",
                value = longBreakDuration,
                range = 1..60,
                unit = "min",
                onValueChange = onLongBreakDurationChange
            )
        }
    }
}

@Composable
fun SettingSlider(
    label: String,
    value: Int,
    range: IntRange,
    unit: String,
    onValueChange: (Int) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = DarkSlateGray
            )
            Text(
                text = "$value $unit",
                style = MaterialTheme.typography.bodyMedium,
                color = Tomato
            )
        }
        
        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = range.first.toFloat()..range.last.toFloat(),
            steps = range.last - range.first - 1,
            colors = SliderDefaults.colors(
                thumbColor = Tomato,
                activeTrackColor = Chocolate,
                inactiveTrackColor = BurlyWood
            )
        )
    }
}

private fun DrawScope.drawAnimatedFire(colors: List<Color>, opacity: Float) {
    val centerX = size.width / 2
    val centerY = size.height / 2
    val radius = size.minDimension / 3
    
    // Draw multiple flame layers
    for (i in 0..2) {
        val layerRadius = radius * (1f - i * 0.2f)
        val layerOpacity = opacity * (1f - i * 0.3f)
        
        val path = Path().apply {
            moveTo(centerX, centerY + layerRadius)
            
            // Create flame shape with curves
            for (angle in 0..360 step 30) {
                val radian = Math.toRadians(angle.toDouble())
                val variation = (sin(radian * 3) * 0.3f + 1f) * layerRadius
                val x = centerX + cos(radian) * variation
                val y = centerY + sin(radian) * variation * 1.5f - layerRadius
                
                if (angle == 0) {
                    moveTo(x.toFloat(), y.toFloat())
                } else {
                    lineTo(x.toFloat(), y.toFloat())
                }
            }
            close()
        }
        
        drawPath(
            path = path,
            color = colors[i % colors.size].copy(alpha = layerOpacity),
        )
    }
}