package com.macgavrina.weatherapp.data.reppository

import com.macgavrina.weatherapp.MainApplication
import com.macgavrina.weatherapp.data.api.ForecastAPI
import com.macgavrina.weatherapp.data.api.WeatherAPI
import com.macgavrina.weatherapp.data.model.City
import com.macgavrina.weatherapp.data.model.HourlyForecast
import com.macgavrina.weatherapp.data.model.WeatherForCity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

object ForecastRepository {

    private val forecastAPI: ForecastAPI = ForecastAPI.create()

    fun getForecastForCity(city: City): Single<HourlyForecast> {
        return forecastAPI.getForecastByCityCoordinates(city.coordinates.lat, city.coordinates.lng)
    }
}