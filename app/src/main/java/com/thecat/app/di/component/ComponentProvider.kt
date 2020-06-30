package com.thecat.app.di.component

import android.content.Context
import com.thecat.app.di.component.AppComponent
import com.thecat.app.di.component.DaggerAppComponent
import com.thecat.app.di.module.RepositoryModule

@Suppress("DEPRECATION")
class ComponentProvider(val context: Context){
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .repositoryModule(RepositoryModule(context))
            .build()
    }
}