package com.example.core_domain.domain.repoImpl

import com.example.core_domain.domain.data.OpenWeatherApi
import com.example.core_domain.domain.data.persistence.CityDao
import com.example.core_domain.domain.data.repositories.ICityRepo
import com.example.core_domain.domain.model.CityInfo
import org.koin.core.component.KoinComponent

class CityRepoImpl(
    private val api: OpenWeatherApi,
    private val cityDao: CityDao,
) : ICityRepo, KoinComponent {
//    private val cityDao: CityDao by inject()

    override suspend fun getCityInfo(cityName: String) =
        api.getCityInfo(cityName)

    override suspend fun getFiveDayForecast(latitude: Double, longitude: Double) =
        api.getFiveDayForecast(latitude, longitude)

    override suspend fun getAirPollution(latitude: Double, longitude: Double) =
        api.getAirPollution(latitude, longitude)

    override suspend fun saveFavouriteCity(city: CityInfo) {
        cityDao.insertFavouriteCity(city)
    }

    override suspend fun getFavouriteCity() =
        cityDao.getFavouriteCity()

    override suspend fun getAllSavedCities() =
        cityDao.getAllSavedCities()

    override suspend fun isCityIsSaved(name: String) =
        cityDao.isCitySaved(name)
}
