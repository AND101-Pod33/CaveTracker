package com.cavemanproductivity.notifications

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationScheduler @Inject constructor(
    private val context: Context
) {
    
    companion object {
        private const val DAILY_REMINDER_WORK = "daily_reminder_work"
        private const val EVENING_REMINDER_WORK = "evening_reminder_work"
    }
    
    fun scheduleDailyReminders() {
        // Morning reminder at 9 AM
        val morningConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresBatteryNotLow(false)
            .build()
        
        val morningReminder = PeriodicWorkRequestBuilder<NotificationWorker>(24, TimeUnit.HOURS)
            .setConstraints(morningConstraints)
            .setInitialDelay(calculateInitialDelay(9, 0), TimeUnit.MILLISECONDS)
            .addTag("morning_reminder")
            .build()
        
        // Evening reminder at 7 PM
        val eveningReminder = PeriodicWorkRequestBuilder<NotificationWorker>(24, TimeUnit.HOURS)
            .setConstraints(morningConstraints)
            .setInitialDelay(calculateInitialDelay(19, 0), TimeUnit.MILLISECONDS)
            .addTag("evening_reminder")
            .build()
        
        WorkManager.getInstance(context).apply {
            enqueueUniquePeriodicWork(
                DAILY_REMINDER_WORK,
                ExistingPeriodicWorkPolicy.REPLACE,
                morningReminder
            )
            
            enqueueUniquePeriodicWork(
                EVENING_REMINDER_WORK,
                ExistingPeriodicWorkPolicy.REPLACE,
                eveningReminder
            )
        }
    }
    
    fun cancelAllReminders() {
        WorkManager.getInstance(context).apply {
            cancelUniqueWork(DAILY_REMINDER_WORK)
            cancelUniqueWork(EVENING_REMINDER_WORK)
        }
    }
    
    fun scheduleTaskReminder(taskId: String, delayInHours: Long) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .build()
        
        val reminderWork = OneTimeWorkRequestBuilder<TaskReminderWorker>()
            .setConstraints(constraints)
            .setInitialDelay(delayInHours, TimeUnit.HOURS)
            .setInputData(
                Data.Builder()
                    .putString("task_id", taskId)
                    .build()
            )
            .addTag("task_reminder_$taskId")
            .build()
        
        WorkManager.getInstance(context).enqueue(reminderWork)
    }
    
    fun cancelTaskReminder(taskId: String) {
        WorkManager.getInstance(context).cancelAllWorkByTag("task_reminder_$taskId")
    }
    
    private fun calculateInitialDelay(targetHour: Int, targetMinute: Int): Long {
        val currentTime = System.currentTimeMillis()
        val calendar = java.util.Calendar.getInstance().apply {
            set(java.util.Calendar.HOUR_OF_DAY, targetHour)
            set(java.util.Calendar.MINUTE, targetMinute)
            set(java.util.Calendar.SECOND, 0)
            set(java.util.Calendar.MILLISECOND, 0)
            
            // If the time has already passed today, schedule for tomorrow
            if (timeInMillis <= currentTime) {
                add(java.util.Calendar.DAY_OF_MONTH, 1)
            }
        }
        
        return calendar.timeInMillis - currentTime
    }
}