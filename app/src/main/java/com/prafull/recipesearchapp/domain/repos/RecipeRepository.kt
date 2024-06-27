package com.prafull.recipesearchapp.domain.repos

import com.prafull.recipesearchapp.domain.models.RecipeInfo
import com.prafull.recipesearchapp.utils.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getRecipe(id: Int): Flow<NetworkResponse<RecipeInfo>>
}
