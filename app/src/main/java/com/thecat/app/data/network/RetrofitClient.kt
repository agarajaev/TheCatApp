package com.thecat.app.data.network

import com.thecat.app.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val retrofit: Retrofit

    val instance: APIService
        get() = retrofit.create(APIService::class.java)

    init {
        val httpClient = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)
        httpClient.networkInterceptors().add(logging)
        httpClient.addInterceptor(AuthInterceptor())

        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.THE_CAT_API)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}