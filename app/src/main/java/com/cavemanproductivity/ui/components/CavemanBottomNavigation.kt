package com.cavemanproductivity.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cavemanproductivity.navigation.CavemanDestinations
import com.cavemanproductivity.ui.theme.*

data class BottomNavItem(
    val route: String,
    val title: String,
    val icon: @Composable () -> Unit,
    val selectedIcon: @Composable () -> Unit = icon
)

@Composable
fun CavemanBottomNavigation(
    navController: NavController,
    currentRoute: String?,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem(
            route = CavemanDestinations.Dashboard.route,
            title = "Tribe",
            icon = { CaveIcons.Cave(color = DarkSlateGray, size = 20) },
            selectedIcon = { CaveIcons.Cave(color = Tomato, size = 20) }
        ),
        BottomNavItem(
            route = CavemanDestinations.HabitCave.route,
            title = "Fires",
            icon = { CaveIcons.Fire(color = DarkSlateGray, size = 20) },
            selectedIcon = { CaveIcons.Fire(color = Tomato, size = 20) }
        ),
        BottomNavItem(
            route = CavemanDestinations.TaskRocks.route,
            title = "Hunt",
            icon = { CaveIcons.Rock(color = DarkSlateGray, size = 20) },
            selectedIcon = { CaveIcons.Rock(color = Tomato, size = 20) }
        ),
        BottomNavItem(
            route = CavemanDestinations.MoonCalendar.route,
            title = "Stars",
            icon = { CaveIcons.Moon(color = DarkSlateGray, size = 20) },
            selectedIcon = { CaveIcons.Moon(color = Tomato, size = 20) }
        ),
        BottomNavItem(
            route = CavemanDestinations.FireFocus.route,
            title = "Rage",
            icon = { CaveIcons.Spear(color = DarkSlateGray, size = 20) },
            selectedIcon = { CaveIcons.Spear(color = Tomato, size = 20) }
        ),
        BottomNavItem(
            route = CavemanDestinations.Stats.route,
            title = "Wisdom",
            icon = { CaveIcons.Trophy(color = DarkSlateGray, size = 20) },
            selectedIcon = { CaveIcons.Trophy(color = Tomato, size = 20) }
        )
    )
    
    val stoneGradient = Brush.verticalGradient(
        colors = listOf(
            StoneMedium,
            StoneDark
        )
    )
    
    NavigationBar(
        modifier = modifier
            .shadow(8.dp, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(brush = stoneGradient)
            .height(80.dp),
        containerColor = Color.Transparent,
        contentColor = DarkSlateGray,
        tonalElevation = 0.dp
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route
            
            NavigationBarItem(
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        if (isSelected) {
                            item.selectedIcon()
                        } else {
                            item.icon()
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) Tomato else DarkSlateGray
                        )
                    }
                },
                selected = isSelected,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Tomato,
                    unselectedIconColor = DarkSlateGray,
                    selectedTextColor = Tomato,
                    unselectedTextColor = DarkSlateGray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}