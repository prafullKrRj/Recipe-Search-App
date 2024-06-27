package com.prafull.recipesearchapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FavouritesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity): Long


    @Query("SELECT * FROM RecipeEntity ORDER by timeStamp DESC")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Query("Delete from RecipeEntity where id = :recipeId")
    suspend fun deleteFromId(recipeId: String)

    @Query("DELETE FROM RecipeEntity")
    suspend fun deleteAll()

    @Query("SELECT * FROM RecipeEntity WHERE id = :id")
    fun getRecipe(id: Int): Flow<RecipeEntity?>
}