package com.macgavrina.weatherapp

import android.app.Application
import android.content.Context
import com.macgavrina.weatherapp.data.db.AppDatabase

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        db = AppDatabase.getInstance(this)
    }

    companion object {

        lateinit var instance: MainApplication
            private set

        lateinit var db:AppDatabase
            private set

        fun applicationContext() : Context {
            return instance.applicationContext
        }

    }

}