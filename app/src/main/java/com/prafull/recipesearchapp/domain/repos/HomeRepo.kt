package com.prafull.recipesearchapp.domain.repos

import com.prafull.recipesearchapp.domain.models.RecipeInfo
import com.prafull.recipesearchapp.utils.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepo {

    suspend fun getRandomRecipes(): Flow<NetworkResponse<List<RecipeInfo>>>

    suspend fun getAllRecipes(
        offset: Int,
        number: Int
    ): Flow<NetworkResponse<List<RecipeInfo>>>
}