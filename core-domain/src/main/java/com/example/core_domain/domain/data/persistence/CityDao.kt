package com.example.core_domain.domain.data.persistence

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.core_domain.domain.model.CityInfo

@Dao
interface CityDao {

    @Upsert
    suspend fun insertFavouriteCity(city: com.example.core_domain.domain.model.CityInfo)

    @Upsert
    suspend fun savedTemporaryCity(city: com.example.core_domain.domain.model.CityInfo)

    @Query("SELECT * FROM CityInfo WHERE NULLIF(cityName, '') is NULL ORDER BY timestamp DESC LIMIT 1")
    suspend fun getFavouriteCity(): com.example.core_domain.domain.model.CityInfo

    @Query("SELECT * FROM CityInfo")
    suspend fun getAllSavedCities(): List<com.example.core_domain.domain.model.CityInfo>

    @Query("SELECT EXISTS (SELECT 1 FROM CityInfo WHERE cityName = :name)")
    suspend fun isCitySaved(name: String): Boolean
}
