package com.thecat.app.di

import android.annotation.SuppressLint
import android.content.Context
import com.thecat.app.di.component.ComponentProvider

object DI {

    @SuppressLint("StaticFieldLeak")
    @JvmStatic
    lateinit var componentProvider: ComponentProvider
        private set

    fun init(applicationContext: Context) {
        componentProvider =
            ComponentProvider(applicationContext)
    }
}