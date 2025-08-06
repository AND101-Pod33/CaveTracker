package com.cavemanproductivity.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.cavemanproductivity.ui.theme.*
import com.cavemanproductivity.data.model.Priority

@Composable
fun StoneTablet(
    modifier: Modifier = Modifier,
    elevation: Int = 4,
    content: @Composable ColumnScope.() -> Unit
) {
    val stoneGradient = Brush.verticalGradient(
        colors = listOf(
            StoneLight,
            StoneMedium,
            StoneDark
        )
    )
    
    Card(
        modifier = modifier
            .shadow(
                elevation = elevation.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = Color.Black.copy(alpha = 0.3f),
                spotColor = Color.Black.copy(alpha = 0.3f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = stoneGradient,
                    shape = RoundedCornerShape(16.dp)
                )
                .border(
                    width = 2.dp,
                    color = Sienna.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}

@Composable
fun StoneButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent,
        contentColor = DarkSlateGray
    ),
    content: @Composable RowScope.() -> Unit
) {
    val stoneGradient = Brush.verticalGradient(
        colors = if (enabled) {
            listOf(StoneMedium, StoneDark, Sienna)
        } else {
            listOf(
                StoneMedium.copy(alpha = 0.6f),
                StoneDark.copy(alpha = 0.6f),
                Sienna.copy(alpha = 0.6f)
            )
        }
    )
    
    Button(
        onClick = onClick,
        modifier = modifier
            .shadow(
                elevation = if (enabled) 6.dp else 2.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                brush = stoneGradient,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 2.dp,
                color = if (enabled) SaddleBrown else SaddleBrown.copy(alpha = 0.4f),
                shape = RoundedCornerShape(12.dp)
            ),
        enabled = enabled,
        colors = colors,
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        content = content
    )
}

@Composable
fun RockChip(
    text: String,
    modifier: Modifier = Modifier,
    size: Priority = Priority.MEDIUM,
    isSelected: Boolean = false
) {
    val chipSize = when (size) {
        Priority.LOW -> 8.dp
        Priority.MEDIUM -> 12.dp
        Priority.HIGH -> 16.dp
        Priority.URGENT -> 20.dp
    }
    
    val chipColor = if (isSelected) Tomato else BurlyWood
    
    Surface(
        modifier = modifier
            .size(chipSize)
            .shadow(2.dp, RoundedCornerShape(chipSize / 2)),
        shape = RoundedCornerShape(chipSize / 2),
        color = chipColor,
        contentColor = if (isSelected) AntiqueWhite else DarkSlateGray
    ) {
        Box(
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1
            )
        }
    }
}