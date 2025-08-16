package com.marcelo.loteriadossonhos.data

import androidx.room.TypeConverter
import java.util.Date

class Converters {

    // Converter Data -> Numero(long)
    @TypeConverter
    fun dateToTimestamp(data: Date): Long{
        return data.time
    }

    // Converter Numero(long) -> Data
    @TypeConverter
    fun timestampToDate(time: Long): Date{
        return Date(time)
    }
}