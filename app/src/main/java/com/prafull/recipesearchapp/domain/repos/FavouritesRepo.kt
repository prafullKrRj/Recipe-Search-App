package com.prafull.recipesearchapp.domain.repos

import com.prafull.recipesearchapp.data.local.RecipeEntity
import com.prafull.recipesearchapp.domain.models.RecipeInfo
import kotlinx.coroutines.flow.Flow

interface FavouritesRepo {
    fun getFavouriteRecipes(): Flow<List<RecipeEntity>>
    suspend fun saveRecipe(recipeInfo: RecipeInfo): Long
    suspend fun removeRecipe(recipeId: String)
    suspend fun deleteAll(): Boolean
    fun getRecipe(id: Int): Flow<RecipeEntity?>
}