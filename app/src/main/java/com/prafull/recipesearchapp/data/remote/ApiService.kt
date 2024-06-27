package com.prafull.recipesearchapp.data.remote

import com.prafull.recipesearchapp.data.dtos.equipmentsDto.EquipmentsDto
import com.prafull.recipesearchapp.data.dtos.recipeInformationDto.RecipeInformationDto
import com.prafull.recipesearchapp.data.dtos.recipeInformationDto.RecipeResponseDto
import com.prafull.recipesearchapp.data.dtos.searchRecipesDto.SearchResultsDto
import com.prafull.recipesearchapp.data.dtos.similarRecipesDto.SimilarRecipesDto
import com.prafull.recipesearchapp.utils.Const
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("recipes/random/")
    suspend fun getPopularRecipes(
        @Query("apiKey") apiKey: String = Const.API_KEY,
        @Query("number") number: Int = 20
    ): RecipeResponseDto

    @GET("recipes/{id}/information")
    suspend fun getRecipeInformation(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = Const.API_KEY,
    ): RecipeInformationDto

    @GET("recipes/{id}/equipmentWidget.json")
    suspend fun getEquipments(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = Const.API_KEY,
    ): EquipmentsDto

    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("apiKey") apiKey: String = Const.API_KEY,
        @Query("query") query: String,
        @Query("number") number: Int = 15
    ): SearchResultsDto

    @GET("recipes/{id}/similar")
    suspend fun getSimilarRecipes(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = Const.API_KEY,
        @Query("number") number: Int = 10,
    ): List<SimilarRecipesDto>

    @GET("recipes/informationBulk")
    suspend fun getRecipeInfoBulk(
        @Query("ids") ids: String,
        @Query("apiKey") apiKey: String = Const.API_KEY,
    ): RecipeResponseDto
}
