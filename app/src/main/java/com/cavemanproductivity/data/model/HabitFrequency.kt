package com.cavemanproductivity.data.model

enum class HabitFrequency(val displayName: String, val daysInterval: Int) {
    DAILY("Daily", 1),
    WEEKLY("Weekly", 7),
    MONTHLY("Monthly", 30),
    CUSTOM("Custom", 0) // Custom intervals handled separately
}