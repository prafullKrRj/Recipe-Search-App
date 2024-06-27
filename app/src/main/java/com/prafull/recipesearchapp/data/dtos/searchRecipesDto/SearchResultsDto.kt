package com.prafull.recipesearchapp.data.dtos.searchRecipesDto

import com.google.gson.annotations.SerializedName


data class SearchResultsDto(

    @SerializedName("offset") var offset: Int? = null,
    @SerializedName("number") var number: Int? = null,
    @SerializedName("results") var results: ArrayList<SearchRecipesDto> = arrayListOf(),
    @SerializedName("totalResults") var totalResults: Int? = null

)