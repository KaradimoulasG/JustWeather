package com.example.justweather.data.persistence

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.justweather.data.dto.cityInfo.CityInfoDto
import com.example.justweather.domain.model.CityInfo

@Dao
interface CityDao {

    @Upsert
    suspend fun insertFavouriteCity(city: CityInfo)

    @Query("SELECT * FROM CityInfo")
    suspend fun getFavouriteCity(): CityInfo

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertSavedCities(cities: List<CityInfo>)
}
