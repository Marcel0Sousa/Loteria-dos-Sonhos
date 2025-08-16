package com.marcelo.loteriadossonhos.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Bet::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun betDao(): BetDao

    companion object {

        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            when {
                instance != null -> {
                    return instance!!
                }

                else -> {
                    instance = buildDatabese(context)
                }
            }
            return instance!!
        }

        private fun buildDatabese(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "loteryApp"
            ).build()
        }
    }
}