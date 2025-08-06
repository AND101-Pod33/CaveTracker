package com.cavemanproductivity.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "daily_quotes")
data class DailyQuote(
    @PrimaryKey val date: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
    val content: String,
    val author: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isFromApi: Boolean = true
)

// Default cave wisdom for offline fallback
object CaveWisdom {
    val defaultQuotes = listOf(
        DailyQuote(
            content = "Strong cave person make fire every day, not just when cold.",
            author = "Ancient Cave Wisdom",
            isFromApi = false
        ),
        DailyQuote(
            content = "Many small rocks build big mountain. Many small habits build strong caveman.",
            author = "Chief Grok",
            isFromApi = false
        ),
        DailyQuote(
            content = "Hunt mammoth one step at a time, or mammoth hunt you.",
            author = "Wise Cave Elder",
            isFromApi = false
        ),
        DailyQuote(
            content = "Sharp spear comes from many sharpenings. Sharp mind comes from many learnings.",
            author = "Tribal Shaman",
            isFromApi = false
        ),
        DailyQuote(
            content = "Cave person who wait for perfect weather never leave cave.",
            author = "Nomad Oog",
            isFromApi = false
        )
    )
}