package com.thecat.app.data.storage

import androidx.room.*
import com.thecat.app.data.model.Cat
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    fun getAll(): Flowable<List<Cat>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cat: Cat): Completable

    @Delete
    fun delete(cat: Cat): Completable
}