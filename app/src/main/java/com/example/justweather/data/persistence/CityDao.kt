package com.example.justweather.data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.justweather.domain.model.CityInfo
import org.jetbrains.annotations.NotNull

@Dao
interface CityDao {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertFavouriteCity(entity: CityInfoEntity): String

//    @Query("SELECT * FROM CityInfoEntity WHERE cityName= :name")
//    suspend fun getFavouriteCity(name: String): CityInfo
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertSavedCities(cities: List<CityInfo>)
}