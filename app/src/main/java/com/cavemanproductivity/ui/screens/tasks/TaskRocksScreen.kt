package com.cavemanproductivity.ui.screens.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cavemanproductivity.data.model.Priority
import com.cavemanproductivity.ui.components.*
import com.cavemanproductivity.ui.screens.tasks.TaskViewModel
import com.cavemanproductivity.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskRocksScreen(
    navController: NavController,
    viewModel: TaskViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "🗿 HUNTING GROUNDS",
            style = MaterialTheme.typography.displayMedium,
            color = SaddleBrown,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Priority Filter Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                FilterChip(
                    selected = uiState.filterPriority == null,
                    onClick = { viewModel.setFilterPriority(null) },
                    label = { Text("All Rocks") }
                )
            }
            items(Priority.values()) { priority ->
                FilterChip(
                    selected = uiState.filterPriority == priority,
                    onClick = { viewModel.setFilterPriority(priority) },
                    label = { Text(priority.displayName) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxWidth().height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Tomato)
            }
        } else if (uiState.tasks.isEmpty()) {
            EmptyTasksState(onAddClick = { viewModel.showAddDialog() })
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.tasks) { task ->
                    TaskCard(
                        task = task,
                        onComplete = { viewModel.completeTask(task.id) },
                        onEdit = { viewModel.startEditingTask(task) },
                        onDelete = { viewModel.deleteTask(task) }
                    )
                }
            }
        }
        
        // Add Task Dialog
        if (uiState.showAddDialog) {
            AddTaskDialog(
                onDismiss = { viewModel.hideAddDialog() },
                onConfirm = { title, description, category, priority, dueDate, estimatedMinutes ->
                    viewModel.addTask(title, description, category, priority, dueDate, estimatedMinutes)
                }
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
fun TaskCard(
    task: com.cavemanproductivity.data.model.Task,
    onComplete: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    StoneTablet(elevation = when (task.priority) {
        Priority.LOW -> 2
        Priority.MEDIUM -> 4
        Priority.HIGH -> 6
        Priority.URGENT -> 8
    }) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = DarkSlateGray,
                        textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                    )
                    if (task.description.isNotEmpty()) {
                        Text(
                            text = task.description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Sienna
                        )
                    }
                }
                
                // Priority Rock
                RockChip(
                    text = when (task.priority) {
                        Priority.LOW -> "🪨"
                        Priority.MEDIUM -> "🗿"
                        Priority.HIGH -> "🪨🪨"
                        Priority.URGENT -> "🏔️"
                    },
                    size = task.priority,
                    isSelected = false
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = task.category,
                        style = MaterialTheme.typography.labelMedium,
                        color = Chocolate
                    )
                    task.dueDate?.let { dueDate ->
                        Text(
                            text = "Due: $dueDate",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isOverdue(dueDate)) OrangeRed else DarkSlateGray
                        )
                    }
                }
                
                if (!task.isCompleted) {
                    StoneButton(onClick = onComplete) {
                        Text("Complete")
                    }
                } else {
                    Text(
                        text = "✅ Done!",
                        style = MaterialTheme.typography.labelMedium,
                        color = OliveDrab
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyTasksState(onAddClick: () -> Unit) {
    StoneTablet {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CaveIcons.Rock(
                color = Sienna, 
                size = 64,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Text(
                text = "No Rocks Yet!",
                style = MaterialTheme.typography.headlineMedium,
                color = SaddleBrown,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Strong caveman organize rocks by size.\nStart collecting your tasks!",
                style = MaterialTheme.typography.bodyLarge,
                color = DarkSlateGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            StoneButton(
                onClick = onAddClick,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Collect First Rock")
            }
        }
    }
}

@Composable
fun AddTaskDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, Priority, String?, Int) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Work") }
    var priority by remember { mutableStateOf(Priority.MEDIUM) }
    var dueDate by remember { mutableStateOf("") }
    var estimatedMinutes by remember { mutableStateOf(30) }
    
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "🗿 Add New Rock",
                style = MaterialTheme.typography.headlineSmall,
                color = SaddleBrown
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Task Title") },
                    placeholder = { Text("Hunt mammoth, Gather berries...") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2
                )
                
                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("Category") },
                    placeholder = { Text("Work, Personal, Hunt...") },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Text(
                    text = "Priority: ${priority.displayName}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = DarkSlateGray
                )
            }
        },
        confirmButton = {
            StoneButton(
                onClick = {
                    if (title.isNotBlank()) {
                        onConfirm(
                            title,
                            description,
                            category,
                            priority,
                            if (dueDate.isNotBlank()) dueDate else null,
                            estimatedMinutes
                        )
                    }
                }
            ) {
                Text("Add Rock")
            }
        },
        dismissButton = {
            StoneButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

private fun isOverdue(dueDate: String): Boolean {
    // Simple check - in a real app you'd parse the date properly
    return false
}