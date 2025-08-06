package com.cavemanproductivity

import android.app.Application
import com.cavemanproductivity.notifications.NotificationScheduler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CavemanApplication : Application() {
    
    @Inject
    lateinit var notificationScheduler: NotificationScheduler
    
    override fun onCreate() {
        super.onCreate()
        
        // Schedule daily notifications
        notificationScheduler.scheduleDailyReminders()
    }
}