package com.cavemanproductivity.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String = "",
    val frequency: HabitFrequency,
    val customInterval: Int = 0, // Used when frequency is CUSTOM
    val category: String,
    val streak: Int = 0,
    val bestStreak: Int = 0,
    val lastCompletedDate: String? = null, // ISO date string
    val createdDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
    val isActive: Boolean = true,
    val color: String = "#FF6347", // Default fire color
    val icon: String = "🔥" // Cave painting style emoji
)