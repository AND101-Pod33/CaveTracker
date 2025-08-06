package com.cavemanproductivity.ui.screens.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.cavemanproductivity.ui.components.CaveIcons
import com.cavemanproductivity.ui.theme.*

@Composable
fun StatsScreen(
    navController: NavController,
    viewModel: StatsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    val stoneGradient = Brush.verticalGradient(
        colors = listOf(
            StoneLight,
            StoneMedium
        )
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = stoneGradient)
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tribe Wisdom",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = DarkSlateGray
            )
            CaveIcons.Trophy(
                color = Gold,
                size = 32
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Overall Progress Card
                StatsCard(
                    title = "Tribe Progress",
                    icon = { CaveIcons.Cave(color = Tomato, size = 24) },
                    content = {
                        Column {
                            StatRow("Total Habits", uiState.totalHabits.toString(), "🔥")
                            StatRow("Completed Today", uiState.completedToday.toString(), "✅")
                            StatRow("Success Rate", "${uiState.successRate}%", "📈")
                        }
                    }
                )
            }
            
            item {
                // Habit Stats Card
                StatsCard(
                    title = "Fire Mastery",
                    icon = { CaveIcons.Fire(color = Orange, size = 24) },
                    content = {
                        Column {
                            StatRow("Active Fires", uiState.activeHabits.toString(), "🔥")
                            StatRow("Streak Leader", uiState.longestStreak.toString(), "🏆")
                            StatRow("Weekly Average", "${uiState.weeklyAverage}%", "📊")
                        }
                    }
                )
            }
            
            item {
                // Task Stats Card
                StatsCard(
                    title = "Hunt Success",
                    icon = { CaveIcons.Rock(color = Brown, size = 24) },
                    content = {
                        Column {
                            StatRow("Total Hunts", uiState.totalTasks.toString(), "🎯")
                            StatRow("Completed Hunts", uiState.completedTasks.toString(), "✅")
                            StatRow("Completion Rate", "${uiState.taskCompletionRate}%", "📈")
                        }
                    }
                )
            }
            
            item {
                // Focus Stats Card
                StatsCard(
                    title = "Rage Sessions",
                    icon = { CaveIcons.Spear(color = Red, size = 24) },
                    content = {
                        Column {
                            StatRow("Total Sessions", uiState.totalFocusSessions.toString(), "⚡")
                            StatRow("Total Time", "${uiState.totalFocusTime}min", "⏱️")
                            StatRow("Average Session", "${uiState.avgFocusSession}min", "📊")
                        }
                    }
                )
            }
            
            item {
                // Weekly Progress Card
                StatsCard(
                    title = "Moon Cycle Progress",
                    icon = { CaveIcons.Moon(color = Silver, size = 24) },
                    content = {
                        Column {
                            StatRow("This Week", "${uiState.weeklyProgress}%", "📅")
                            StatRow("Last Week", "${uiState.lastWeekProgress}%", "📅")
                            StatRow("Improvement", "${uiState.improvement}%", "📈")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun StatsCard(
    title: String,
    icon: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.9f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                icon()
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = DarkSlateGray
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            content()
        }
    }
}

@Composable
fun StatRow(
    label: String,
    value: String,
    emoji: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = DarkSlateGray
            )
        }
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = Tomato
        )
    }
} 