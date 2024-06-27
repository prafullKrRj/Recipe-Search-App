package com.prafull.recipesearchapp.data.repos

import com.prafull.recipesearchapp.data.remote.ApiService
import com.prafull.recipesearchapp.domain.mappers.toRecipeResponse
import com.prafull.recipesearchapp.domain.models.RecipeInfo
import com.prafull.recipesearchapp.domain.repos.HomeRepo
import com.prafull.recipesearchapp.utils.NetworkResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeRepoImpl : HomeRepo, KoinComponent {
    private val apiService by inject<ApiService>()


    override suspend fun getRandomRecipes(): Flow<NetworkResponse<List<RecipeInfo>>> {
        return callbackFlow {
            try {
                val response = apiService.getPopularRecipes().toRecipeResponse()
                trySend(NetworkResponse.Success(response.recipes))
            } catch (e: retrofit2.HttpException) {
                trySend(NetworkResponse.Error(e.localizedMessage ?: "No internet"))
            } catch (e: Exception) {
                trySend(NetworkResponse.Error(e.localizedMessage ?: "An Error Occurred"))
            }
            awaitClose { }
        }
    }

    override suspend fun getAllRecipes(
        offset: Int,
        number: Int
    ): Flow<NetworkResponse<List<RecipeInfo>>> {
        return callbackFlow {

            awaitClose { }
        }
    }

}