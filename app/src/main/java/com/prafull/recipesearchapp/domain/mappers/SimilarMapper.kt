package com.prafull.recipesearchapp.domain.mappers

import com.prafull.recipesearchapp.data.dtos.similarRecipesDto.SimilarRecipesDto
import com.prafull.recipesearchapp.domain.models.SimilarRecipe

fun SimilarRecipesDto.toSimilarRecipe(): SimilarRecipe {
    return SimilarRecipe(
            id = id ?: 0,
            title = title ?: "",
            imageType = imageType ?: "",
            readyInMinutes = readyInMinutes.toString() ?: "",
            servings = servings ?: 0
    )
}