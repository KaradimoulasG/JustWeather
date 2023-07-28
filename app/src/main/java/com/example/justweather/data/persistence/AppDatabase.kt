package com.example.justweather.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.justweather.domain.model.CityInfo

@Database(entities = [CityInfo::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao
}