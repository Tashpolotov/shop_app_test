package com.example.shop_app_test

import android.app.Application
import com.example.shop_app_test.data.room.AppDB
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App:Application() {
    lateinit var database: AppDB
        private set

    override fun onCreate() {
        super.onCreate()
        database = AppDB.getDatabase(this)
    }
}