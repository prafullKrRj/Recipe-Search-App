package com.prafull.recipesearchapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RecipeEntity::class], version = 1)
@TypeConverters(FavTypeConverters::class)
abstract class FavouritesDatabase : RoomDatabase() {
    abstract fun favouritesDao(): FavouritesDao

    companion object {
        @Volatile
        private var INSTANCE: FavouritesDatabase? = null
        fun getInstance(context: Context): FavouritesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context,
                        FavouritesDatabase::class.java,
                        "favourites_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}