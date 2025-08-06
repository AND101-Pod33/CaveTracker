package com.cavemanproductivity.ui.screens.calendar

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
fun MoonCalendarScreen(navController: NavController) {
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
                CaveIcons.Moon(color = DarkGoldenrod, size = 64)
                Text(
                    text = "🌙 MOON CALENDAR",
                    style = MaterialTheme.typography.displayMedium,
                    color = SaddleBrown,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Track time by moon cycles and seasons.\nCaveman know when to hunt, when to rest!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkSlateGray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}