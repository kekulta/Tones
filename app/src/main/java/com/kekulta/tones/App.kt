package com.kekulta.tones

import android.app.Application
import com.kekulta.tones.di.koinModule
import com.kekulta.tones.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        startKoin {
            androidContext(instance)
            modules(
                viewModelsModule,
                koinModule,
            )
        }
    }


    companion object {
        lateinit var instance: App
            private set
    }
}