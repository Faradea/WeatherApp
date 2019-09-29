package com.macgavrina.weatherapp.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.macgavrina.weatherapp.LOG_TAG
import com.macgavrina.weatherapp.MainApplication
import com.macgavrina.weatherapp.data.model.City
import com.macgavrina.weatherapp.data.model.CityWithWeather
import com.macgavrina.weatherapp.data.model.HourlyForecastElement
import com.macgavrina.weatherapp.domain.usecase.CityUseCase
import com.macgavrina.weatherapp.domain.usecase.ForecastUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CityDetailsViewModel(application: Application) : AndroidViewModel(MainApplication.instance) {

    private val compositeDisposable = CompositeDisposable()
    private var selectedCityWithWeather = MutableLiveData<CityWithWeather>()
    private var hourForecast = MutableLiveData<List<HourlyForecastElement>>()

    fun getSelectedCity(): LiveData<CityWithWeather> {
        return selectedCityWithWeather
    }

    fun getForecast(): LiveData<List<HourlyForecastElement>> {
        return hourForecast
    }

    fun setSelectedCityId(cityId: Int) {
        loadCityDetails(cityId)
    }

    private fun loadCityDetails(cityId: Int) {
        compositeDisposable.add(
            CityUseCase.getCityDetails(cityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { cityDetails ->
                    this.selectedCityWithWeather.value = CityWithWeather(cityDetails, null)
                    //ToDo Fix RxJava hell
                    loadCityWeather(cityDetails)
                    loadCityForecast(cityDetails)
                })
    }

    private fun loadCityWeather(city: City) {
        compositeDisposable.add(
            CityUseCase.getWeatherForCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ weather ->
                    Log.d(LOG_TAG, "Weather for city is received from API, $weather")
                    //ToDo calculate datetime based on timezone
//                    this.selectedCity.value?.airTemp = weather.main.temp
//                    this.selectedCity.value?.humidity = weather.main.humidity
                    this.selectedCityWithWeather.postValue(CityWithWeather(city, weather))
                }, {error ->
                    Log.e(LOG_TAG, "error loading weather for city, $error")
                })
        )
    }

    private fun loadCityForecast(city: City) {
        compositeDisposable.add(
            ForecastUseCase.getForecastForCity(city)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ weather ->
                    Log.d(LOG_TAG, "Forecast for city is received from API, $weather")
                    hourForecast.value = weather.list
                }, {error ->
                    Log.e(LOG_TAG, "error loading forecast for city, $error")
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}