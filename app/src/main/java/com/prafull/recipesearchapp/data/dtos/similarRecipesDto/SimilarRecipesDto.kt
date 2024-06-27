package com.prafull.recipesearchapp.data.dtos.similarRecipesDto

import com.google.gson.annotations.SerializedName

data class SimilarRecipesResponse(
    @SerializedName("similar") var similar: List<SimilarRecipesDto>? = null
)

data class SimilarRecipesDto(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("imageType") var imageType: String? = null,
    @SerializedName("readyInMinutes") var readyInMinutes: Int? = null,
    @SerializedName("servings") var servings: Int? = null,
    @SerializedName("sourceUrl") var sourceUrl: String? = null

)