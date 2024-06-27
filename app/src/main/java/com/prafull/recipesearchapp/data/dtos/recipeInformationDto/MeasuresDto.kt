package com.prafull.recipesearchapp.data.dtos.recipeInformationDto

import com.google.gson.annotations.SerializedName


data class MeasuresDto(

    @SerializedName("metric") var metric: MetricDto? = MetricDto(),
    @SerializedName("us") var us: UsDto? = UsDto()

)