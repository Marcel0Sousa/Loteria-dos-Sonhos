package com.marcelo.loteriadossonhos

import android.app.Application
import com.marcelo.loteriadossonhos.data.AppDatabase

class App: Application() {

    lateinit var appDatabse: AppDatabase

    override fun onCreate() {
        super.onCreate()

        appDatabse = AppDatabase.getInstance(this)
    }
}