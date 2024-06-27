package com.prafull.recipesearchapp.data.dtos.recipeInformationDto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class WinePairingDto(

    @SerializedName("pairedWines") var pairedWines: ArrayList<String> = arrayListOf(),
    @SerializedName("pairingText") var pairingText: String? = null,
    @SerializedName("productMatches") var productMatches: ArrayList<ProductMatches> = arrayListOf()

)