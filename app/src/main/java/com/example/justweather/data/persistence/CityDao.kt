package com.example.justweather.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.justweather.domain.model.CityInfo

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteCity(name: String)

    @Query("SELECT * FROM CityInfo WHERE cityName= :name")
    suspend fun getFavouriteCity(name: String): CityInfo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedCities(cities: List<CityInfo>)
}