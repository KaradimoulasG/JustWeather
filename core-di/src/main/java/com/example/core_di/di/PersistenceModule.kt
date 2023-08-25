package com.example.core_di.di

import androidx.room.Room
import com.example.core_domain.domain.data.persistence.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val PersistenceModule = module {

    single {
        Room.databaseBuilder(
            androidApplication(),
            AppDatabase::class.java,
            "appDatabase",
        ).fallbackToDestructiveMigration()
            .build()
    }

    single { get<AppDatabase>().cityDao }
}
