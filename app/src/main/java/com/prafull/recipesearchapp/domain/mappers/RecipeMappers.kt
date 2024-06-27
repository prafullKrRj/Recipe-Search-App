package com.prafull.recipesearchapp.domain.mappers

import com.prafull.recipesearchapp.data.dtos.equipmentsDto.EquipmentDto
import com.prafull.recipesearchapp.data.dtos.recipeInformationDto.ExtendedIngredientsDto
import com.prafull.recipesearchapp.data.dtos.recipeInformationDto.RecipeInformationDto
import com.prafull.recipesearchapp.data.dtos.recipeInformationDto.RecipeResponseDto
import com.prafull.recipesearchapp.domain.models.Equipment
import com.prafull.recipesearchapp.domain.models.Ingredients
import com.prafull.recipesearchapp.domain.models.RecipeInfo
import com.prafull.recipesearchapp.domain.models.RecipeResponse

fun RecipeResponseDto.toRecipeResponse(): RecipeResponse {
    return RecipeResponse(
            recipes = recipes.map {
                it.toRecipeInfo()
            }
    )
}

fun RecipeInformationDto.toRecipeInfo(): RecipeInfo {
    return RecipeInfo(
            id = id?.toInt() ?: 0,
            title = title ?: "",
            image = image ?: "",
            readyInMinutes = readyInMinutes.toString(),
            pricePerServing = pricePerServing.toString(),
            instructions = instructions.toString(),
            ingredients = extendedIngredients.map {
                it.toIngredients()
            }.toMutableList(),
            equipments = equipments?.equipments?.map {
                it.toEquipment()
            }?.toMutableList() ?: mutableListOf(),
            summary = summary ?: "",
            servings = servings?.toInt() ?: 0
    )
}

fun ExtendedIngredientsDto.toIngredients(): Ingredients {
    return Ingredients(
            id = id?.toInt() ?: 0,
            image = image ?: "",
            name = name ?: "",
            originalName = originalName ?: ""
    )
}

fun EquipmentDto.toEquipment(): Equipment {
    return Equipment(
            name = name ?: "",
            image = image ?: ""
    )
}