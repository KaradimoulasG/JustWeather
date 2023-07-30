package com.example.justweather.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteCity(entity: CityInfoEntity)

//    @Query("SELECT * FROM CityInfoEntity WHERE cityName= :name")
//    suspend fun getFavouriteCity(name: String): CityInfo
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertSavedCities(cities: List<CityInfo>)
}
