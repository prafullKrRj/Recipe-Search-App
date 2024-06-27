package com.prafull.recipesearchapp.data.repos

import coil.network.HttpException
import com.prafull.recipesearchapp.data.remote.ApiService
import com.prafull.recipesearchapp.domain.mappers.toRecipeInfo
import com.prafull.recipesearchapp.domain.models.RecipeInfo
import com.prafull.recipesearchapp.domain.repos.RecipeRepository
import com.prafull.recipesearchapp.utils.NetworkResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RecipeRepoImpl : RecipeRepository, KoinComponent {
    private val apiService by inject<ApiService>()
    override suspend fun getRecipe(id: Int): Flow<NetworkResponse<RecipeInfo>> {
        return callbackFlow {
            try {
                val response = apiService.getRecipeInformation(id = id)
                val responseEquipments = apiService.getEquipments(id = id)
                response.apply {
                    equipments = responseEquipments
                }
                trySend(NetworkResponse.Success(response.toRecipeInfo()))
            } catch (e: HttpException) {
                trySend(NetworkResponse.Error(e.localizedMessage ?: "No internet"))
            } catch (e: Exception) {
                trySend(NetworkResponse.Error(e.localizedMessage ?: "An error occurred"))
            }
            awaitClose { }
        }
    }

}