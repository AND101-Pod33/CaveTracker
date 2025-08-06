package com.cavemanproductivity.ui.screens.focus

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cavemanproductivity.ui.components.CaveIcons
import com.cavemanproductivity.ui.components.StoneTablet
import com.cavemanproductivity.ui.theme.*

@Composable
fun FireFocusScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StoneTablet {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CaveIcons.Spear(color = Chocolate, size = 64)
                Text(
                    text = "🔥 FIRE FOCUS",
                    style = MaterialTheme.typography.displayMedium,
                    color = SaddleBrown,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Focus like caveman focus on hunt.\nPomodoro fire timer and stopwatch for deep work!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkSlateGray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}