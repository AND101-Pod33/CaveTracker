package com.cavemanproductivity.ui.screens.calendar

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cavemanproductivity.ui.components.CaveIcons
import com.cavemanproductivity.ui.components.StoneButton
import com.cavemanproductivity.ui.components.StoneTablet
import com.cavemanproductivity.ui.theme.*

@Composable
fun MoonCalendarScreen(
    navController: NavController,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "🌙 STAR MAP",
            style = MaterialTheme.typography.displayMedium,
            color = SaddleBrown,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Month Navigation
        MonthNavigationHeader(
            month = uiState.currentMonth,
            year = uiState.currentYear,
            onPreviousMonth = { viewModel.navigateToPreviousMonth() },
            onNextMonth = { viewModel.navigateToNextMonth() }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Calendar Grid
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Tomato)
            }
        } else {
            StoneTablet {
                Column {
                    // Days of week header
                    WeekHeader()
                    
                    // Calendar grid
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(7),
                        modifier = Modifier.height(300.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(uiState.days) { day ->
                            CalendarDayCell(
                                day = day,
                                isSelected = day.date == uiState.selectedDate,
                                onClick = { viewModel.selectDate(day.date) }
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Selected day details
        uiState.selectedDate?.let { selectedDate ->
            SelectedDayDetails(
                date = selectedDate,
                tasks = uiState.selectedDayTasks,
                habits = uiState.selectedDayHabits
            )
        }
        
        // Error handling
        uiState.error?.let { error ->
            LaunchedEffect(error) {
                viewModel.clearError()
            }
        }
    }
}

@Composable
fun MonthNavigationHeader(
    month: String,
    year: Int,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousMonth) {
            Icon(
                Icons.Default.KeyboardArrowLeft,
                contentDescription = "Previous Month",
                tint = Chocolate
            )
        }
        
        Text(
            text = "$month $year",
            style = MaterialTheme.typography.headlineMedium,
            color = Chocolate,
            fontWeight = FontWeight.Bold
        )
        
        IconButton(onClick = onNextMonth) {
            Icon(
                Icons.Default.KeyboardArrowRight,
                contentDescription = "Next Month",
                tint = Chocolate
            )
        }
    }
}

@Composable
fun WeekHeader() {
    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        daysOfWeek.forEach { day ->
            Text(
                text = day,
                style = MaterialTheme.typography.labelMedium,
                color = DarkSlateGray,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
        }
    }
    
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun CalendarDayCell(
    day: CalendarDay,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isSelected -> Tomato.copy(alpha = 0.3f)
        day.isToday -> DarkGoldenrod.copy(alpha = 0.3f)
        day.hasEvents -> Chocolate.copy(alpha = 0.2f)
        else -> Color.Transparent
    }
    
    val textColor = when {
        !day.isCurrentMonth -> DarkSlateGray.copy(alpha = 0.4f)
        day.isToday -> Tomato
        day.hasEvents -> DarkSlateGray
        else -> DarkSlateGray.copy(alpha = 0.7f)
    }
    
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = day.dayOfMonth.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = textColor,
                fontWeight = if (day.isToday) FontWeight.Bold else FontWeight.Normal
            )
            
            // Event indicators
            if (day.hasEvents) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    if (day.tasks.isNotEmpty()) {
                        Canvas(modifier = Modifier.size(4.dp)) {
                            drawCircle(
                                color = SaddleBrown,
                                radius = size.minDimension / 2
                            )
                        }
                    }
                    if (day.completedHabits.isNotEmpty()) {
                        Canvas(modifier = Modifier.size(4.dp)) {
                            drawCircle(
                                color = Tomato,
                                radius = size.minDimension / 2
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SelectedDayDetails(
    date: String,
    tasks: List<com.cavemanproductivity.data.model.Task>,
    habits: List<com.cavemanproductivity.data.model.Habit>
) {
    StoneTablet {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "📅 $date",
                style = MaterialTheme.typography.titleLarge,
                color = Chocolate,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            
            if (tasks.isEmpty() && habits.isEmpty()) {
                Text(
                    text = "🌙 Peaceful cave day - no events",
                    style = MaterialTheme.typography.bodyMedium,
                    color = DarkSlateGray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                // Tasks section
                if (tasks.isNotEmpty()) {
                    Text(
                        text = "🗿 Tasks (${tasks.size})",
                        style = MaterialTheme.typography.titleMedium,
                        color = SaddleBrown
                    )
                    
                    tasks.forEach { task ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "• ${task.title}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = DarkSlateGray,
                                modifier = Modifier.weight(1f)
                            )
                            
                            Text(
                                text = task.priority.displayName,
                                style = MaterialTheme.typography.labelSmall,
                                color = Chocolate
                            )
                        }
                    }
                }
                
                // Habits section
                if (habits.isNotEmpty()) {
                    if (tasks.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    
                    Text(
                        text = "🔥 Completed Habits (${habits.size})",
                        style = MaterialTheme.typography.titleMedium,
                        color = Tomato
                    )
                    
                    habits.forEach { habit ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "• ${habit.icon} ${habit.name}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = DarkSlateGray
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${habit.streak} 🔥",
                                style = MaterialTheme.typography.labelSmall,
                                color = Tomato
                            )
                        }
                    }
                }
            }
        }
    }
}