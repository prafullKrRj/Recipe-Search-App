package com.prafull.recipesearchapp.domain.repos

import com.prafull.recipesearchapp.domain.models.SearchResults
import com.prafull.recipesearchapp.domain.models.SimilarRecipe
import com.prafull.recipesearchapp.utils.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepo {

    suspend fun getResponses(query: String): Flow<NetworkResponse<SearchResults>>
    suspend fun getSimilarRecipes(id: Int): Flow<NetworkResponse<List<SimilarRecipe>>>
}