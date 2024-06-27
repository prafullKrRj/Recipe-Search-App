package com.prafull.recipesearchapp.data.dtos.recipeInformationDto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ProductMatches(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("price") var price: String? = null,
    @SerializedName("imageUrl") var imageUrl: String? = null,
    @SerializedName("averageRating") var averageRating: Double? = null,
    @SerializedName("ratingCount") var ratingCount: Double? = null,
    @SerializedName("score") var score: Double? = null,
    @SerializedName("link") var link: String? = null

)