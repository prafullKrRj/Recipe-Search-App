package com.prafull.recipesearchapp.data.dtos.recipeInformationDto

import com.google.gson.annotations.SerializedName

data class UsDto(

    @SerializedName("amount") var amount: Double? = null,
    @SerializedName("unitLong") var unitLong: String? = null,
    @SerializedName("unitShort") var unitShort: String? = null

)