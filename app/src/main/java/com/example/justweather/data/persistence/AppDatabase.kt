package com.example.justweather.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.justweather.domain.model.CityInfo

@Database(entities = [CityInfo::class], version = 1)
@TypeConverters(WeatherTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {

//    companion object {
//        fun newInstance(context: Context): AppDatabase {
//            return Room.databaseBuilder(
//                context,
//                AppDatabase::class.java,
//                "appDatabase",
//            ).fallbackToDestructiveMigration()
//                .build()
//        }
//    }

    abstract val cityDao: CityDao
}
