package com.cavemanproductivity.ui.screens.settings

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
fun ChiefSettingsScreen(navController: NavController) {
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
                CaveIcons.StickFigure(color = DarkSlateGray, size = 64)
                Text(
                    text = "👑 CHIEF SETTINGS",
                    style = MaterialTheme.typography.displayMedium,
                    color = SaddleBrown,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Customize your cave like wise chief.\nBone toggles and stone preferences!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DarkSlateGray,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}