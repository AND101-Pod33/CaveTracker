package com.cavemanproductivity.ui.screens.habits

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
fun HabitCaveScreen(navController: NavController) {
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
                CaveIcons.Fire(color = Tomato, size = 64)
                Text(
                    text = "🔥 HABIT CAVE",
                    style = MaterialTheme.typography.displayMedium,
                    color = SaddleBrown,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Build strong habits like ancient caveman build fire.\nComing soon to your cave!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkSlateGray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}