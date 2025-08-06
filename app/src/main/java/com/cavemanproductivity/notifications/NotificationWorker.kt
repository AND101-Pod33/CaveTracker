package com.cavemanproductivity.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cavemanproductivity.MainActivity
import com.cavemanproductivity.R
import com.cavemanproductivity.data.repository.HabitRepository
import com.cavemanproductivity.data.repository.TaskRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.text.SimpleDateFormat
import java.util.*

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val habitRepository: HabitRepository,
    private val taskRepository: TaskRepository
) : CoroutineWorker(context, workerParams) {

    companion object {
        const val HABIT_CHANNEL_ID = "habit_reminders"
        const val TASK_CHANNEL_ID = "task_reminders"
        const val HABIT_NOTIFICATION_ID = 1001
        const val TASK_NOTIFICATION_ID = 1002
    }

    override suspend fun doWork(): Result {
        return try {
            createNotificationChannels()
            checkHabitReminders()
            checkTaskReminders()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val habitChannel = NotificationChannel(
                HABIT_CHANNEL_ID,
                "Habit Fire Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Reminders to keep your habit fires burning"
            }

            val taskChannel = NotificationChannel(
                TASK_CHANNEL_ID,
                "Task Rock Reminders",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Reminders about your task rocks"
            }

            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(habitChannel)
            notificationManager.createNotificationChannel(taskChannel)
        }
    }

    private suspend fun checkHabitReminders() {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val habits = habitRepository.getAllActiveHabits()
        
        habits.collect { habitList ->
            val uncompletedHabits = habitList.filter { habit ->
                habit.lastCompletedDate != today
            }
            
            if (uncompletedHabits.isNotEmpty()) {
                val habitNames = uncompletedHabits.take(3).joinToString(", ") { it.name }
                val title = if (uncompletedHabits.size == 1) {
                    "🔥 Keep Your Fire Burning!"
                } else {
                    "🔥 ${uncompletedHabits.size} Fires Need Attention"
                }
                
                val message = if (uncompletedHabits.size == 1) {
                    "Time to complete: $habitNames"
                } else {
                    "Complete: $habitNames${if (uncompletedHabits.size > 3) " and ${uncompletedHabits.size - 3} more" else ""}"
                }
                
                showNotification(
                    channelId = HABIT_CHANNEL_ID,
                    notificationId = HABIT_NOTIFICATION_ID,
                    title = title,
                    message = message,
                    icon = R.drawable.ic_launcher
                )
            }
        }
    }

    private suspend fun checkTaskReminders() {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val tasks = taskRepository.getTasksByDate(today)
        
        tasks.collect { taskList ->
            val incompleteTasks = taskList.filter { !it.isCompleted }
            
            if (incompleteTasks.isNotEmpty()) {
                val taskTitles = incompleteTasks.take(3).joinToString(", ") { it.title }
                val title = if (incompleteTasks.size == 1) {
                    "🗿 Rock Needs Moving!"
                } else {
                    "🗿 ${incompleteTasks.size} Rocks Waiting"
                }
                
                val message = if (incompleteTasks.size == 1) {
                    "Task due today: $taskTitles"
                } else {
                    "Tasks due: $taskTitles${if (incompleteTasks.size > 3) " and ${incompleteTasks.size - 3} more" else ""}"
                }
                
                showNotification(
                    channelId = TASK_CHANNEL_ID,
                    notificationId = TASK_NOTIFICATION_ID,
                    title = title,
                    message = message,
                    icon = R.drawable.ic_launcher
                )
            }
        }
    }

    private fun showNotification(
        channelId: String,
        notificationId: Int,
        title: String,
        message: String,
        icon: Int
    ) {
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        with(NotificationManagerCompat.from(applicationContext)) {
            notify(notificationId, notification)
        }
    }
}