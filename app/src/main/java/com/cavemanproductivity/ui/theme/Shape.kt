package com.cavemanproductivity.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val CavemanShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp), // Stone pebble
    small = RoundedCornerShape(12.dp),     // Small stone
    medium = RoundedCornerShape(16.dp),    // Medium rock
    large = RoundedCornerShape(24.dp),     // Large boulder
    extraLarge = RoundedCornerShape(32.dp) // Cave entrance
)