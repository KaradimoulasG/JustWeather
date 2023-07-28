package com.example.justweather.di

import androidx.room.Room
import com.example.justweather.data.persistence.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val persistenceModule = module(createdAtStart = true) {

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "appDatabase",
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().cityDao() }
}
