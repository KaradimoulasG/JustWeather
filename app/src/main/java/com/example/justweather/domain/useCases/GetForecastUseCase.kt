package com.example.justweather.domain.useCases

import com.example.justweather.data.repositories.ICityRepo

class GetForecastUseCase(
    private val cityRepo: ICityRepo,
) {
    suspend operator fun invoke(lat: Double, lon: Double) =
        cityRepo.getFiveDayForecast(lat, lon)
}
