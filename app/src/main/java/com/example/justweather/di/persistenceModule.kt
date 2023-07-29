package com.example.justweather.di

import com.example.justweather.data.persistence.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val persistenceModule = module {

    single<AppDatabase> {
        AppDatabase.newInstance(androidContext())
    }

    single { get<AppDatabase>().cityDao() }
}
