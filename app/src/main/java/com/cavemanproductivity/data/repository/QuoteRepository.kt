package com.cavemanproductivity.data.repository

import com.cavemanproductivity.data.api.QuotableApi
import com.cavemanproductivity.data.database.dao.DailyQuoteDao
import com.cavemanproductivity.data.model.CaveWisdom
import com.cavemanproductivity.data.model.DailyQuote
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuoteRepository @Inject constructor(
    private val quotableApi: QuotableApi,
    private val dailyQuoteDao: DailyQuoteDao
) {
    suspend fun getTodaysQuote(): DailyQuote {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        
        // Check if we already have today's quote
        val existingQuote = dailyQuoteDao.getQuoteForDate(today)
        if (existingQuote != null) {
            return existingQuote
        }
        
        // Try to fetch from API
        return try {
            val response = quotableApi.getRandomQuote(minLength = 50, maxLength = 200)
            if (response.isSuccessful && response.body() != null) {
                val apiQuote = response.body()!!
                val dailyQuote = DailyQuote(
                    date = today,
                    content = apiQuote.content,
                    author = apiQuote.author,
                    timestamp = System.currentTimeMillis(),
                    isFromApi = true
                )
                dailyQuoteDao.insertQuote(dailyQuote)
                dailyQuote
            } else {
                getRandomCaveWisdom(today)
            }
        } catch (e: Exception) {
            getRandomCaveWisdom(today)
        }
    }
    
    private suspend fun getRandomCaveWisdom(date: String): DailyQuote {
        val randomWisdom = CaveWisdom.defaultQuotes.random()
        val dailyQuote = randomWisdom.copy(
            date = date,
            timestamp = System.currentTimeMillis()
        )
        dailyQuoteDao.insertQuote(dailyQuote)
        return dailyQuote
    }
    
    fun getAllQuotes(): Flow<List<DailyQuote>> {
        return dailyQuoteDao.getAllQuotes()
    }
    
    suspend fun cleanupOldQuotes() {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -30)
        val cutoffDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        dailyQuoteDao.deleteOldQuotes(cutoffDate)
    }
}