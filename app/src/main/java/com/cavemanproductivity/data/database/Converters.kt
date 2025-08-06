package com.cavemanproductivity.data.database

import androidx.room.TypeConverter
import com.cavemanproductivity.data.model.HabitFrequency
import com.cavemanproductivity.data.model.Priority
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class Converters {
    private val moshi = Moshi.Builder().build()
    private val listStringAdapter = moshi.adapter<List<String>>(
        Types.newParameterizedType(List::class.java, String::class.java)
    )

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        return listStringAdapter.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        return listStringAdapter.fromJson(value) ?: emptyList()
    }

    @TypeConverter
    fun fromPriority(priority: Priority): String {
        return priority.name
    }

    @TypeConverter
    fun toPriority(priority: String): Priority {
        return Priority.valueOf(priority)
    }

    @TypeConverter
    fun fromHabitFrequency(frequency: HabitFrequency): String {
        return frequency.name
    }

    @TypeConverter
    fun toHabitFrequency(frequency: String): HabitFrequency {
        return HabitFrequency.valueOf(frequency)
    }
}