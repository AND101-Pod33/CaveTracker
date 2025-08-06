package com.cavemanproductivity.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cavemanproductivity.navigation.CavemanDestinations
import com.cavemanproductivity.ui.components.CaveIcons
import com.cavemanproductivity.ui.components.StoneButton
import com.cavemanproductivity.ui.components.StoneTablet
import com.cavemanproductivity.ui.theme.*

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            // Header
            Text(
                text = "🏔️ CAVE DASHBOARD",
                style = MaterialTheme.typography.displayMedium,
                color = SaddleBrown,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        
        item {
            // Daily Wisdom Quote
            DailyWisdomCard(
                quote = uiState.dailyQuote,
                isLoading = uiState.isLoading
            )
        }
        
        item {
            // Stats Overview
            StatsOverview(
                stats = uiState.stats,
                isLoading = uiState.isLoading
            )
        }
        
        item {
            // Quick Actions
            QuickActions(navController = navController)
        }
        
        uiState.error?.let { error ->
            item {
                ErrorCard(
                    error = error,
                    onRetry = { viewModel.refreshDashboard() },
                    onDismiss = { viewModel.clearError() }
                )
            }
        }
    }
}

@Composable
fun DailyWisdomCard(
    quote: com.cavemanproductivity.data.model.DailyQuote?,
    isLoading: Boolean
) {
    StoneTablet(elevation = 6) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "📜",
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "DAILY CAVE WISDOM",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Chocolate
                )
            }
            
            if (isLoading) {
                CircularProgressIndicator(color = Tomato)
            } else if (quote != null) {
                Text(
                    text = "\"${quote.content}\"",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = DarkSlateGray
                )
                
                Text(
                    text = "— ${quote.author}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic,
                    textAlign = TextAlign.End,
                    color = Sienna,
                    modifier = Modifier.fillMaxWidth()
                )
                
                if (!quote.isFromApi) {
                    Text(
                        text = "🔥 Ancient Cave Wisdom",
                        style = MaterialTheme.typography.labelSmall,
                        color = Tomato
                    )
                }
            }
        }
    }
}

@Composable
fun StatsOverview(
    stats: DashboardStats,
    isLoading: Boolean
) {
    StoneTablet {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "🏹 TRIBE STATS",
                style = MaterialTheme.typography.headlineSmall,
                color = Chocolate,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Tomato)
                }
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    item {
                        StatCard(
                            title = "Active Habits",
                            value = stats.activeHabits.toString(),
                            icon = { CaveIcons.Fire(color = Tomato, size = 24) }
                        )
                    }
                    item {
                        StatCard(
                            title = "Fire Streaks",
                            value = stats.totalStreaks.toString(),
                            icon = { CaveIcons.Fire(color = FireOrange, size = 24) }
                        )
                    }
                    item {
                        StatCard(
                            title = "Active Tasks",
                            value = stats.activeTasks.toString(),
                            icon = { CaveIcons.Rock(color = SaddleBrown, size = 24) }
                        )
                    }
                    item {
                        StatCard(
                            title = "Completed",
                            value = stats.completedTasks.toString(),
                            icon = { CaveIcons.Spear(color = OliveDrab, size = 24) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .width(100.dp)
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = BurlyWood.copy(alpha = 0.7f)
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            icon()
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = DarkSlateGray
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                color = DarkSlateGray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun QuickActions(navController: NavController) {
    StoneTablet {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "⚡ QUICK HUNT",
                style = MaterialTheme.typography.headlineSmall,
                color = Chocolate,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StoneButton(
                    onClick = { navController.navigate(CavemanDestinations.HabitCave.route) },
                    modifier = Modifier.weight(1f)
                ) {
                    CaveIcons.Fire(color = DarkSlateGray, size = 16)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Habits")
                }
                
                StoneButton(
                    onClick = { navController.navigate(CavemanDestinations.TaskRocks.route) },
                    modifier = Modifier.weight(1f)
                ) {
                    CaveIcons.Rock(color = DarkSlateGray, size = 16)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tasks")
                }
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StoneButton(
                    onClick = { navController.navigate(CavemanDestinations.FireFocus.route) },
                    modifier = Modifier.weight(1f)
                ) {
                    CaveIcons.Spear(color = DarkSlateGray, size = 16)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Focus")
                }
                
                StoneButton(
                    onClick = { navController.navigate(CavemanDestinations.MoonCalendar.route) },
                    modifier = Modifier.weight(1f)
                ) {
                    CaveIcons.Moon(color = DarkSlateGray, size = 16)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Calendar")
                }
            }
        }
    }
}

@Composable
fun ErrorCard(
    error: String,
    onRetry: () -> Unit,
    onDismiss: () -> Unit
) {
    StoneTablet {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "⚠️ Cave Trouble",
                style = MaterialTheme.typography.titleMedium,
                color = OrangeRed
            )
            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                color = DarkSlateGray
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StoneButton(
                    onClick = onRetry,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Try Again")
                }
                StoneButton(
                    onClick = onDismiss,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Dismiss")
                }
            }
        }
    }
}