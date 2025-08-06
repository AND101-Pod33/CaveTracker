package com.cavemanproductivity.di

import android.content.Context
import androidx.room.Room
import com.cavemanproductivity.data.database.CavemanDatabase
import com.cavemanproductivity.data.database.dao.DailyQuoteDao
import com.cavemanproductivity.data.database.dao.HabitDao
import com.cavemanproductivity.data.database.dao.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CavemanDatabase {
        return Room.databaseBuilder(
            context,
            CavemanDatabase::class.java,
            "caveman_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideHabitDao(database: CavemanDatabase): HabitDao {
        return database.habitDao()
    }

    @Provides
    fun provideTaskDao(database: CavemanDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    fun provideDailyQuoteDao(database: CavemanDatabase): DailyQuoteDao {
        return database.dailyQuoteDao()
    }
}