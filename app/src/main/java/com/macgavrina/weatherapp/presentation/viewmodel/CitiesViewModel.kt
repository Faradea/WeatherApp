package com.macgavrina.weatherapp.presentation.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.macgavrina.weatherapp.LOG_TAG
import com.macgavrina.weatherapp.MainApplication
import com.macgavrina.weatherapp.data.model.City
import com.macgavrina.weatherapp.data.model.CityWithWeather
import com.macgavrina.weatherapp.domain.usecase.CityUseCase
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CitiesViewModel(application: Application) : AndroidViewModel(MainApplication.instance) {

    private val compositeDisposable = CompositeDisposable()
    private var citiesWithWeather = MutableLiveData<List<CityWithWeather>>()

    init {
        loadCities()
    }

    fun getCities(): LiveData<List<CityWithWeather>> {
        return citiesWithWeather
    }

    fun newCityWasAdded() {
        loadCities()
    }

    private fun loadCities() {
        compositeDisposable.add(
            CityUseCase.getAllCities()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ cities ->
                    val citiesWithWeatherList = mutableListOf<CityWithWeather>()
                    cities.forEach { city ->
                        citiesWithWeatherList.add(CityWithWeather(city, null))
                        getWeatherForCity(city)
                    }
                    citiesWithWeather.postValue(citiesWithWeatherList)
                }, {error ->
                    Log.e(LOG_TAG, "Error loading cities, $error")
                })
        )
    }

    private fun getWeatherForCity(city: City) {
        compositeDisposable.add(
            CityUseCase.getWeatherForCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ weatherForCity ->
                    Log.d(LOG_TAG, "Weather for city with id = ${city.uid} is received from server, $weatherForCity")
                    val citiesList = citiesWithWeather.value?.toMutableList()
                    var i = 0
                    citiesList?.forEach { cityFromList ->
                        if (cityFromList.city.uid == city.uid) {
                            citiesList[i].weatherForCity = weatherForCity
                        }
                        i += 1
                    }
                    this.citiesWithWeather.postValue(citiesList)
                }, {error ->
                    Log.e(LOG_TAG, "Error loading weather for city, $error")
                    //ToDo MANDATORY Display toast
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}