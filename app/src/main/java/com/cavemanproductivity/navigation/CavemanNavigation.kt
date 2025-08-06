package com.cavemanproductivity.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cavemanproductivity.ui.screens.dashboard.DashboardScreen
import com.cavemanproductivity.ui.screens.habits.HabitCaveScreen
import com.cavemanproductivity.ui.screens.tasks.TaskRocksScreen
import com.cavemanproductivity.ui.screens.calendar.MoonCalendarScreen
import com.cavemanproductivity.ui.screens.focus.FireFocusScreen
import com.cavemanproductivity.ui.screens.stats.StatsScreen
import com.cavemanproductivity.ui.screens.settings.ChiefSettingsScreen

@Composable
fun CavemanNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = CavemanDestinations.Dashboard.route,
        modifier = modifier,
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(300)
            ) + fadeIn(animationSpec = tween(300))
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(300)
            ) + fadeOut(animationSpec = tween(300))
        }
    ) {
        composable(CavemanDestinations.Dashboard.route) {
            DashboardScreen(navController = navController)
        }
        
        composable(CavemanDestinations.HabitCave.route) {
            HabitCaveScreen(navController = navController)
        }
        
        composable(CavemanDestinations.TaskRocks.route) {
            TaskRocksScreen(navController = navController)
        }
        
        composable(CavemanDestinations.MoonCalendar.route) {
            MoonCalendarScreen(navController = navController)
        }
        
        composable(CavemanDestinations.FireFocus.route) {
            FireFocusScreen(navController = navController)
        }
        
        composable(CavemanDestinations.Stats.route) {
            StatsScreen(navController = navController)
        }
        
        composable(CavemanDestinations.ChiefSettings.route) {
            ChiefSettingsScreen(navController = navController)
        }
    }
}