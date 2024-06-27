package com.prafull.recipesearchapp.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class SimilarRecipe(
    val id: Int,
    val title: String,
    val imageType: String,
    val readyInMinutes: String,
    val servings: Int,
)