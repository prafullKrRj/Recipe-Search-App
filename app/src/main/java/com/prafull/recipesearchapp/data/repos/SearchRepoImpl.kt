package com.prafull.recipesearchapp.data.repos

import coil.network.HttpException
import com.prafull.recipesearchapp.data.remote.ApiService
import com.prafull.recipesearchapp.domain.mappers.toSearchResults
import com.prafull.recipesearchapp.domain.mappers.toSimilarRecipe
import com.prafull.recipesearchapp.domain.models.SearchResults
import com.prafull.recipesearchapp.domain.models.SimilarRecipe
import com.prafull.recipesearchapp.domain.repos.SearchRepo
import com.prafull.recipesearchapp.utils.NetworkResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class SearchRepoImpl : SearchRepo, KoinComponent {

    private val apiService by inject<ApiService>()


    override suspend fun getResponses(query: String): Flow<NetworkResponse<SearchResults>> {
        return callbackFlow {
            try {
                val response = apiService.searchRecipes(query = query)
                trySend(NetworkResponse.Success(response.toSearchResults()))
            } catch (e: HttpException) {
                trySend(NetworkResponse.Error(e.localizedMessage ?: "No internet"))
            } catch (e: Exception) {
                trySend(NetworkResponse.Error(e.localizedMessage ?: "An error occurred"))
            }
            awaitClose { }
        }
    }

    override suspend fun getSimilarRecipes(id: Int): Flow<NetworkResponse<List<SimilarRecipe>>> {
        return callbackFlow {
            try {
                val similarResponse = apiService.getSimilarRecipes(id).map {
                    it.toSimilarRecipe()
                }
                trySend(NetworkResponse.Success(similarResponse))
            } catch (e: HttpException) {
                trySend(NetworkResponse.Error(e.localizedMessage ?: "No internet"))
            } catch (e: Exception) {
                trySend(NetworkResponse.Error(e.localizedMessage ?: "An error occurred"))
            }
            awaitClose { }
        }
    }

}