package com.prafull.recipesearchapp.data.dtos.equipmentsDto

import com.google.gson.annotations.SerializedName

data class EquipmentDto(
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String
)