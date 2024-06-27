package com.prafull.recipesearchapp.data.local

import androidx.room.Entity
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prafull.recipesearchapp.domain.models.Equipment
import com.prafull.recipesearchapp.domain.models.Ingredients
import com.prafull.recipesearchapp.domain.models.Nutrition
import com.prafull.recipesearchapp.domain.models.RecipeInfo
import com.prafull.recipesearchapp.domain.models.SimilarRecipe

@Entity(tableName = "RecipeEntity", primaryKeys = ["id", "timeStamp"])
data class RecipeEntity(
    val id: String,
    val title: String = "",
    val image: ByteArray,
    val readyInMinutes: String = "",
    val pricePerServing: String = "",
    val servings: Int = 0,
    val instructions: String = "",
    val summary: String = "",
    val ingredients: MutableList<Ingredients> = mutableListOf(),
    val equipments: MutableList<Equipment> = mutableListOf(),
    val nutrition: MutableList<Nutrition> = mutableListOf(),
    val similarRecipes: MutableList<SimilarRecipe> = mutableListOf(),
    val timeStamp: Long = System.currentTimeMillis()
) {
    fun toRecipeInfo(): RecipeInfo {
        return RecipeInfo(
                id = id.toInt(),
                title = title,
                image = "",
                imageByteArray = image,
                readyInMinutes = readyInMinutes,
                pricePerServing = pricePerServing,
                servings = servings,
                instructions = instructions,
                summary = summary,
                ingredients = ingredients,
                equipments = equipments,
                nutrition = nutrition,
                similarRecipes = similarRecipes
        )
    }
}

class FavTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromIngredientsList(list: List<Ingredients>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toIngredientsList(data: String): List<Ingredients> {
        val listType = object : TypeToken<List<Ingredients>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromEquipmentsList(list: List<Equipment>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toEquipmentsList(data: String): List<Equipment> {
        val listType = object : TypeToken<List<Equipment>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromNutritionList(list: List<Nutrition>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toNutritionList(data: String): List<Nutrition> {
        val listType = object : TypeToken<List<Nutrition>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromSimilarRecipesList(list: List<SimilarRecipe>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toSimilarRecipesList(data: String): List<SimilarRecipe> {
        val listType = object : TypeToken<List<SimilarRecipe>>() {}.type
        return gson.fromJson(data, listType)
    }
}