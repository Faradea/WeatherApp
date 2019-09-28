package com.macgavrina.weatherapp.domain.usecase

import com.macgavrina.weatherapp.data.model.City
import com.macgavrina.weatherapp.data.model.HourlyForecast
import com.macgavrina.weatherapp.data.reppository.ForecastRepository
import io.reactivex.Single

object ForecastUseCase {

    private val forecastRepository = ForecastRepository

    fun getForecastForCity(city: City): Single<HourlyForecast> {
        return forecastRepository.getForecastForCity(city)
    }
}