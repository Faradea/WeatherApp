package com.macgavrina.weatherapp.support

import android.icu.text.SimpleDateFormat

//ToDo Use kotlin extension for it
object DateFormatter {

    fun formatDateAndTimeFromTimestamp(timestamp: Int): String {
        return SimpleDateFormat("dd.MM.yyyy HH:mm").format(timestamp*1000L)
    }

    fun formatShortDateAndTimeFromTimestamp(timestamp: Int): String {
        return SimpleDateFormat("HH:mm (dd.MM)").format(timestamp*1000L)
    }
}