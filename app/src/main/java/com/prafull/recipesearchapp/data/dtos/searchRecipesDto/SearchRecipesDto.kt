package com.prafull.recipesearchapp.data.dtos.searchRecipesDto

import com.google.gson.annotations.SerializedName


data class SearchRecipesDto(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("imageType") var imageType: String? = null

)