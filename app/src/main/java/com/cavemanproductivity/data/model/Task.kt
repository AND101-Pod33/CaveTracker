package com.cavemanproductivity.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String = "",
    val category: String,
    val dueDate: String? = null, // ISO date string
    val dueTime: String? = null, // ISO time string  
    val priority: Priority,
    val isCompleted: Boolean = false,
    val completedDate: String? = null, // ISO date string
    val createdDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
    val tags: List<String> = emptyList(),
    val estimatedMinutes: Int = 0,
    val actualMinutes: Int = 0
)