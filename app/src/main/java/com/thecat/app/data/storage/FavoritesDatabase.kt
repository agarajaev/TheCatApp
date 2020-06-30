package com.thecat.app.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thecat.app.data.model.Cat

@Database(entities = [Cat::class], version = 1)
abstract class FavoritesDatabase : RoomDatabase() {

    abstract fun favoritesDao(): FavoritesDao

    companion object {

        @Volatile
        private var INSTANCE: FavoritesDatabase? = null

        fun getInstance(context: Context): FavoritesDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(
                        context
                    )
                        .also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FavoritesDatabase::class.java, "Favorites.db"
            ).build()
    }
}