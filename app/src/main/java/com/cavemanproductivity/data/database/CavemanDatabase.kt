package com.cavemanproductivity.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.cavemanproductivity.data.database.dao.DailyQuoteDao
import com.cavemanproductivity.data.database.dao.HabitDao
import com.cavemanproductivity.data.database.dao.TaskDao
import com.cavemanproductivity.data.model.DailyQuote
import com.cavemanproductivity.data.model.Habit
import com.cavemanproductivity.data.model.Task

@Database(
    entities = [Habit::class, Task::class, DailyQuote::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CavemanDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun taskDao(): TaskDao
    abstract fun dailyQuoteDao(): DailyQuoteDao

    companion object {
        @Volatile
        private var INSTANCE: CavemanDatabase? = null

        fun getDatabase(context: Context): CavemanDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CavemanDatabase::class.java,
                    "caveman_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}