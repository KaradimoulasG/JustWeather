package com.example.justweather.data.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [CityInfoEntity::class], version = 1, exportSchema = false)
@TypeConverters(WeatherTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun newInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "appDatabase",
            ).fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun cityDao(): CityDao
}
