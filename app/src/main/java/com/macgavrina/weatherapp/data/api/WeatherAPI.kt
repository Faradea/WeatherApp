package com.macgavrina.weatherapp.data.api

import com.google.gson.GsonBuilder
import com.macgavrina.weatherapp.data.model.WeatherForCity
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

const val SERVER_URL:String = "https://samples.openweathermap.org/data/2.5/"
const val APP_ID_FOR_OPEN_WEATHER = "b6907d289e10d714a6e88b30761fae22"

interface WeatherAPI {
    @GET("weather")
    fun getWeatherByCityCoordinates(@Query("lat") lat:Float,
                                    @Query("lon") lng: Float,
                                    @Query("appid") appId: String = APP_ID_FOR_OPEN_WEATHER
    ): Single<WeatherForCity>

    companion object ApiFactory{
        fun create():WeatherAPI{

            val gson = GsonBuilder().create()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(SERVER_URL)
                .build()
            return retrofit.create(WeatherAPI::class.java)
        }

    }
}