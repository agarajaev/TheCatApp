package com.thecat.app.data

import android.content.Context
import com.thecat.app.data.model.Cat
import com.thecat.app.data.network.APIService
import com.thecat.app.data.network.RetrofitClient
import com.thecat.app.data.network.response.CatResponse
import com.thecat.app.data.storage.FavoritesDao
import com.thecat.app.data.storage.FavoritesDatabase
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

class Repository(context: Context) : APIService, FavoritesDao {

    companion object {

        @Volatile
        private var INSTANCE: Repository? = null

        fun getInstance(context: Context): Repository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Repository(context).also { INSTANCE = it }
            }
    }

    private var networkService: APIService = RetrofitClient().instance
    private var favoritesDao: FavoritesDao = FavoritesDatabase.getInstance(context).favoritesDao()

    override fun getAllCats(limit: Int, page: Int): Observable<List<CatResponse>> {
        return networkService.getAllCats(limit = limit, page = page)
    }

    override fun getAll(): Flowable<List<Cat>> {
        return favoritesDao.getAll()
    }

    override fun insert(cat: Cat): Completable {
        return favoritesDao.insert(cat)
    }

    override fun delete(cat: Cat): Completable {
        return favoritesDao.delete(cat)
    }
}