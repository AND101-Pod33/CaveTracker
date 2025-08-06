package com.cavemanproductivity.ui.screens.tasks

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
fun TaskRocksScreen(navController: NavController) {
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
                CaveIcons.Rock(color = SaddleBrown, size = 64)
                Text(
                    text = "🗿 TASK ROCKS",
                    style = MaterialTheme.typography.displayMedium,
                    color = SaddleBrown,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Organize tasks like caveman organize rocks.\nSmall rocks, big rocks, all rocks have purpose!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkSlateGray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}