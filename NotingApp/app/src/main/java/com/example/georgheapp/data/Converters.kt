package com.example.georgheapp.data

import androidx.room.TypeConverter
import java.util.*

//Display Date In Note - ToDo
class Converters {
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar =
        Calendar.getInstance().apply { timeInMillis = value }
}