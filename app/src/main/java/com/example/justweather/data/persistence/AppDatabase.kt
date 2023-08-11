package com.example.justweather.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.justweather.domain.model.CityInfo

@Database(entities = [CityInfo::class], version = 1)
@TypeConverters(WeatherTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val cityDao: CityDao
}
