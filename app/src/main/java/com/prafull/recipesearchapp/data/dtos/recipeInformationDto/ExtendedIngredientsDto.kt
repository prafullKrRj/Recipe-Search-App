package com.prafull.recipesearchapp.data.dtos.recipeInformationDto

import com.google.gson.annotations.SerializedName

data class ExtendedIngredientsDto(

    @SerializedName("aisle") var aisle: String? = null,
    @SerializedName("amount") var amount: Double? = null,
    @SerializedName("consitency") var consitency: String? = null,
    @SerializedName("id") var id: Double? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("measures") var measures: MeasuresDto? = MeasuresDto(),
    @SerializedName("meta") var meta: ArrayList<String> = arrayListOf(),
    @SerializedName("name") var name: String? = null,
    @SerializedName("original") var original: String? = null,
    @SerializedName("originalName") var originalName: String? = null,
    @SerializedName("unit") var unit: String? = null,
)