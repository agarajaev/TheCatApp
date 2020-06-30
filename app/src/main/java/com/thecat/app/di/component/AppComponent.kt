package com.thecat.app.di.component

import com.thecat.app.App
import com.thecat.app.MainActivity
import com.thecat.app.di.module.RepositoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class])
interface AppComponent {
    fun inject(application: App)

    fun inject(mainActivity: MainActivity)
}
