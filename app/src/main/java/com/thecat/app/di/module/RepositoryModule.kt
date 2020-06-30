package com.thecat.app.di.module

import android.content.Context
import androidx.room.Room
import com.thecat.app.BuildConfig
import com.thecat.app.data.Repository
import com.thecat.app.data.network.APIService
import com.thecat.app.data.network.AuthInterceptor
import com.thecat.app.data.storage.FavoritesDao
import com.thecat.app.data.storage.FavoritesDatabase
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RepositoryModule(val context: Context) {

    @Provides
    @Singleton
    fun provideRepository(networkService: APIService, favoritesDao: FavoritesDao) =
        Repository(context)

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
        httpClient.networkInterceptors().add(logging)
        httpClient.addInterceptor(authInterceptor)
        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideAPIService(okHttpClient: OkHttpClient): APIService = Retrofit.Builder()
        .baseUrl(BuildConfig.THE_CAT_API)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(APIService::class.java)

    @Provides
    @Singleton
    fun provideFavoritesDB() = Room.databaseBuilder(
        context.applicationContext,
        FavoritesDatabase::class.java, "Favorites.db"
    ).build()

    @Provides
    @Singleton
    fun provideFavoritesDao(favoritesDatabase: FavoritesDatabase) = favoritesDatabase.favoritesDao()
}