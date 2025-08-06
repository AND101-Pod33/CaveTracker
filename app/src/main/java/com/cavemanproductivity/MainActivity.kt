package com.cavemanproductivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cavemanproductivity.navigation.CavemanDestinations
import com.cavemanproductivity.navigation.CavemanNavigation
import com.cavemanproductivity.ui.components.CavemanBottomNavigation
import com.cavemanproductivity.ui.theme.CavemanProductivityTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        setContent {
            CavemanProductivityTheme {
                CavemanApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CavemanApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    // Determine if bottom navigation should be shown
    val showBottomNav = when (currentRoute) {
        CavemanDestinations.Dashboard.route,
        CavemanDestinations.HabitCave.route,
        CavemanDestinations.TaskRocks.route,
        CavemanDestinations.MoonCalendar.route,
        CavemanDestinations.FireFocus.route,
        CavemanDestinations.ChiefSettings.route -> true
        else -> false
    }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomNav) {
                CavemanBottomNavigation(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        }
    ) { innerPadding ->
        CavemanNavigation(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}