package com.rubenabad.teamworkprojects

import android.app.Application
import com.rubenabad.teamworkprojects.di.appModule
import org.koin.android.ext.android.startKoin


class KoinApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(appModule))
    }
}