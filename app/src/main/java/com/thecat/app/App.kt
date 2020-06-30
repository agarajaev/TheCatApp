package com.thecat.app

import android.app.Application
import com.thecat.app.di.DI

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DI.init(this)
    }

}