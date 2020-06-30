package com.thecat.app.data.network

import com.thecat.app.data.network.response.CatResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Provides retrofit requests service
 */
interface APIService {

    @GET("images/search")
    fun getAllCats(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Observable<List<CatResponse>>
}