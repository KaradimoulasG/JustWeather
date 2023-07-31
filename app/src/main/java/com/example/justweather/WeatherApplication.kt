package com.example.justweather

import android.app.Application
import com.example.justweather.di.ApiModule
import com.example.justweather.di.PersistenceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@WeatherApplication)
            modules(
                PersistenceModule,
                ApiModule,
            )
        }
    }
}
