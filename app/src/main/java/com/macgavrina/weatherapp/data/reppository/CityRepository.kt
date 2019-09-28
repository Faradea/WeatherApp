package com.macgavrina.weatherapp.data.reppository

import com.macgavrina.weatherapp.MainApplication
import com.macgavrina.weatherapp.data.api.WeatherAPI
import com.macgavrina.weatherapp.data.model.City
import com.macgavrina.weatherapp.data.model.WeatherForCity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

object CityRepository {

    private val cityDAO = MainApplication.db.cityDAO()
    private var allCities = cityDAO.getAll()
    val weatherAPI: WeatherAPI = WeatherAPI.create()

    fun getAllCities(): Single<List<City>> {
        return cityDAO.getAll()
    }

    fun addCity(city: City): Completable {
        return cityDAO.insertCity(city)
    }

    fun getCityById(cityId: Int): Maybe<City> {
        return cityDAO.getCity(cityId)
    }

    fun getWeatherForCity(city: City): Single<WeatherForCity> {
        return weatherAPI.getWeatherByCityCoordinates(city.coordinates.lat, city.coordinates.lng)
    }
}