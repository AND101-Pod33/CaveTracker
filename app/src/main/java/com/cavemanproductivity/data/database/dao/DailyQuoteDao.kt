package com.cavemanproductivity.data.database.dao

import androidx.room.*
import com.cavemanproductivity.data.model.DailyQuote
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyQuoteDao {
    @Query("SELECT * FROM daily_quotes WHERE date = :date")
    suspend fun getQuoteForDate(date: String): DailyQuote?

    @Query("SELECT * FROM daily_quotes ORDER BY date DESC LIMIT 1")
    suspend fun getLatestQuote(): DailyQuote?

    @Query("SELECT * FROM daily_quotes ORDER BY date DESC")
    fun getAllQuotes(): Flow<List<DailyQuote>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: DailyQuote)

    @Query("DELETE FROM daily_quotes WHERE date < :cutoffDate")
    suspend fun deleteOldQuotes(cutoffDate: String)

    @Query("SELECT COUNT(*) FROM daily_quotes")
    suspend fun getQuoteCount(): Int
}