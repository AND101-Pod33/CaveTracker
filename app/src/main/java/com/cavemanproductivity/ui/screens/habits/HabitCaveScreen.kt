package com.cavemanproductivity.ui.screens.habits

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cavemanproductivity.ui.components.*
import com.cavemanproductivity.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HabitCaveScreen(
    navController: NavController,
    viewModel: HabitViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header
            Text(
                text = "🔥 SACRED FIRES",
                style = MaterialTheme.typography.displayMedium,
                color = SaddleBrown,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Tomato)
                }
            } else if (uiState.habits.isEmpty()) {
                EmptyHabitsState(onAddClick = { viewModel.showAddDialog() })
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.habits) { habit ->
                        HabitCard(
                            habit = habit,
                            isCompletedToday = viewModel.isHabitCompletedToday(habit),
                            onComplete = { viewModel.completeHabit(habit.id) },
                            onEdit = { viewModel.startEditingHabit(habit) },
                            onDelete = { viewModel.deleteHabit(habit) }
                        )
                    }
                }
            }
        }
        
        // Add/Edit Dialog
        if (uiState.showAddDialog) {
            AddHabitDialog(
                onDismiss = { viewModel.hideAddDialog() },
                onConfirm = { name, description, frequency, category, customInterval ->
                    viewModel.addHabit(name, description, frequency, category, customInterval)
                }
            )
        }
        
        // Error handling
        uiState.error?.let { error ->
            LaunchedEffect(error) {
                // Show error snackbar or dialog
                viewModel.clearError()
            }
        }
    }
}

@Composable
fun HabitCard(
    habit: com.cavemanproductivity.data.model.Habit,
    isCompletedToday: Boolean,
    onComplete: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    StoneTablet(elevation = if (isCompletedToday) 8 else 4) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${habit.icon} ${habit.name}",
                        style = MaterialTheme.typography.titleLarge,
                        color = if (isCompletedToday) OliveDrab else DarkSlateGray
                    )
                    if (habit.description.isNotEmpty()) {
                        Text(
                            text = habit.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Sienna
                        )
                    }
                }
                
                Text(
                    text = "${habit.streak} 🔥",
                    style = MaterialTheme.typography.headlineSmall,
                    color = if (habit.streak > 0) Tomato else DarkSlateGray
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${habit.frequency.displayName} • ${habit.category}",
                    style = MaterialTheme.typography.labelMedium,
                    color = Chocolate
                )
                
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (!isCompletedToday) {
                        StoneButton(onClick = onComplete) {
                            CaveIcons.Fire(color = DarkSlateGray, size = 16)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Complete")
                        }
                    } else {
                        Text(
                            text = "✅ Done Today!",
                            style = MaterialTheme.typography.labelMedium,
                            color = OliveDrab
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyHabitsState(onAddClick: () -> Unit) {
    StoneTablet {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CaveIcons.Fire(
                color = Sienna, 
                size = 64,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "No Fire Yet!",
                style = MaterialTheme.typography.headlineMedium,
                color = SaddleBrown,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Strong caveman need good habits.\nStart building fire of discipline!",
                style = MaterialTheme.typography.bodyLarge,
                color = DarkSlateGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            StoneButton(
                onClick = onAddClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Light First Fire")
            }
        }
    }
}

@Composable
fun AddHabitDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, com.cavemanproductivity.data.model.HabitFrequency, String, Int) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var frequency by remember { mutableStateOf(com.cavemanproductivity.data.model.HabitFrequency.DAILY) }
    var category by remember { mutableStateOf("Health") }
    var customInterval by remember { mutableStateOf(1) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "🔥 Light New Fire",
                style = MaterialTheme.typography.headlineSmall,
                color = SaddleBrown
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Habit Name") },
                    placeholder = { Text("Morning hunt, Evening fire...") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description (optional)") },
                    placeholder = { Text("Why this habit important?") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2
                )
                
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    placeholder = { Text("Health, Work, Learning...") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                // Frequency selection would go here
                Text(
                    text = "Frequency: ${frequency.displayName}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = DarkSlateGray
                )
            }
        },
        confirmButton = {
            StoneButton(
                onClick = {
                    if (name.isNotBlank()) {
                        onConfirm(name, description, frequency, category, customInterval)
                    }
                }
            ) {
                Text("Light Fire")
            }
        },
        dismissButton = {
            StoneButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}