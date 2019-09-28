package com.macgavrina.weatherapp.data.api

import com.google.gson.GsonBuilder
import com.macgavrina.weatherapp.data.model.HourlyForecast
import com.macgavrina.weatherapp.data.model.WeatherForCity
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val FORECAST_API_URL:String = "https://samples.openweathermap.org/data/2.5/forecast/"

interface ForecastAPI {
    @GET("hourly")
    fun getForecastByCityCoordinates(@Query("lat") lat:Float,
                                    @Query("lon") lng: Float,
                                    @Query("appid") appId: String = APP_ID_FOR_OPEN_WEATHER
    ): Single<HourlyForecast>

    companion object ApiFactory{
        fun create():ForecastAPI{

            val gson = GsonBuilder().create()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(FORECAST_API_URL)
                .build()
            return retrofit.create(ForecastAPI::class.java)
        }

    }
}