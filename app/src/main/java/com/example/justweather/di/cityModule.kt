package com.example.justweather.di

import com.example.justweather.data.repositories.ICityRepo
import com.example.justweather.domain.repoImpl.CityRepoImpl
import com.example.justweather.domain.useCases.GetForecastUseCase
import com.example.justweather.presentation.WeatherViewModel
import org.koin.dsl.module

val cityModule = module {

    single<ICityRepo> { CityRepoImpl(get(), get()) }

    single { WeatherViewModel(get(), get()) }

    single { GetForecastUseCase(get()) }
}
