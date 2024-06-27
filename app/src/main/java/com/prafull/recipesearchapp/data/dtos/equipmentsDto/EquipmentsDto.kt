package com.prafull.recipesearchapp.data.dtos.equipmentsDto

import com.google.gson.annotations.SerializedName

data class EquipmentsDto(
    @SerializedName("equipment") val equipments: List<EquipmentDto> = listOf()
)