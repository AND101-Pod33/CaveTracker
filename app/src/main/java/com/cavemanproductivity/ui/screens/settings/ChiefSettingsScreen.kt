package com.cavemanproductivity.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cavemanproductivity.ui.components.CaveIcons
import com.cavemanproductivity.ui.components.StoneTablet
import com.cavemanproductivity.ui.theme.*

@Composable
fun ChiefSettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Text(
            text = "👑 WARRIOR'S DEN",
            style = MaterialTheme.typography.displayMedium,
            color = SaddleBrown,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        
        // Notification Settings
        SettingsSection(
            title = "🔔 Cave Notifications",
            icon = { CaveIcons.Fire(color = Tomato, size = 24) }
        ) {
            SettingsItem(
                title = "Daily Reminders",
                description = "Get notified about habits and tasks"
            )
        }
        
        // App Info
        SettingsSection(
            title = "🎨 Cave Theme",
            icon = { CaveIcons.Cave(color = SaddleBrown, size = 24) }
        ) {
            SettingsItem(
                title = "Stone Theme Active",
                description = "Classic caveman aesthetic with earth tones"
            )
        }
        
        // Focus Settings  
        SettingsSection(
            title = "🔥 Focus Timer",
            icon = { CaveIcons.Spear(color = Chocolate, size = 24) }
        ) {
            SettingsItem(
                title = "Work Sessions",
                description = "25 minutes focus • 5 min break • 15 min long break"
            )
        }
        
        // About Section
        SettingsSection(
            title = "📜 About",
            icon = { CaveIcons.StickFigure(color = DarkSlateGray, size = 24) }
        ) {
            SettingsItem(
                title = "Caveman Productivity",
                description = "Ancient wisdom for modern productivity • v1.0"
            )
        }
    }
}

@Composable
fun SettingsSection(
    title: String,
    icon: @Composable () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    StoneTablet {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                icon()
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = Chocolate
                )
            }
            
            content()
        }
    }
}

@Composable
fun SettingsItem(
    title: String,
    description: String,
    trailing: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = DarkSlateGray
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = Sienna
            )
        }
        
        trailing?.invoke()
    }
}