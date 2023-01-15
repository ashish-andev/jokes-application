package com.ashish.myapplication

import android.app.Application
import com.ashish.myapplication.di.AppModule
import com.ashish.myapplication.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class JokesApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@JokesApplication)
            modules(listOf(AppModule, NetworkModule))
        }
    }
}