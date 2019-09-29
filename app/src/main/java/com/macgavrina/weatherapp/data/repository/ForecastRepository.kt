package com.macgavrina.weatherapp.data.repository

import com.macgavrina.weatherapp.data.api.ForecastAPI
import com.macgavrina.weatherapp.data.model.City
import com.macgavrina.weatherapp.data.model.HourlyForecast
import io.reactivex.Single

object ForecastRepository {

    private val forecastAPI: ForecastAPI = ForecastAPI.create()

    fun getForecastForCity(city: City): Single<HourlyForecast> {
        return forecastAPI.getForecastByCityCoordinates(city.coordinates.lat, city.coordinates.lng)
    }
}