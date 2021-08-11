package com.ncl.app.base

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (instance == null) {
            instance = this
        }

    }

    companion object {
        private var instance: BaseApplication? = null

        fun getInstance(): BaseApplication? {
            return instance
        }
    }

}