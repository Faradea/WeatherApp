package com.macgavrina.weatherapp.utils

import android.icu.text.SimpleDateFormat
import android.os.Build

//ToDo Use kotlin extension for it
object DateFormatter {

    fun formatDateAndTimeFromTimestamp(timestamp: Int): String {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val vv = SimpleDateFormat("dd.MM.yyyy HH:mm").format(timestamp*1000L)
            return vv
        } else {
            //TODO("VERSION.SDK_INT < N")
            return timestamp.toString()
        }
    }

    fun formatTimeFromTimestamp(timestamp: Int): String {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val vv = SimpleDateFormat("HH:mm").format(timestamp*1000L)
            return vv
        } else {
            //TODO("VERSION.SDK_INT < N")
            return timestamp.toString()
        }
    }
}