package com.prafull.recipesearchapp.domain.models

import kotlinx.serialization.Serializable

data class RecipeResponse(
    val recipes: List<RecipeInfo> = arrayListOf()
)

@Serializable
data class RecipeInfo(
    val id: Int = 0,
    val title: String = "",
    val image: String = "",
    val imageByteArray: ByteArray = byteArrayOf(),
    val readyInMinutes: String = "",
    val pricePerServing: String = "",
    val servings: Int = 0,
    val instructions: String = "",
    val summary: String = "",
    val ingredients: MutableList<Ingredients> = mutableListOf(),
    val equipments: MutableList<Equipment> = mutableListOf(),
    val nutrition: MutableList<Nutrition> = mutableListOf(),
    val similarRecipes: MutableList<SimilarRecipe> = mutableListOf()
)

@Serializable
data class Ingredients(
    val id: Int? = 0,
    var image: String = "",
    val name: String = "",
    val originalName: String = "",
    var imageByteArray: ByteArray = byteArrayOf()
)

@Serializable
data class Equipment(
    var image: String,
    val name: String,
    var imageByteArray: ByteArray = byteArrayOf()
)

@Serializable
data class Nutrition(
    val id: String = ""
)