package com.prafull.recipesearchapp.data.dtos.recipeInformationDto

import com.google.gson.annotations.SerializedName
import com.prafull.recipesearchapp.data.dtos.equipmentsDto.EquipmentsDto

data class RecipeResponseDto(
    @SerializedName("recipes") var recipes: ArrayList<RecipeInformationDto> = arrayListOf()
)

data class AnalyzedInstructionDto(
    @SerializedName("step") var step: String? = null,
        // Add other fields if needed
)

data class RecipeInformationDto(

    @SerializedName("id") var id: Double? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("imageType") var imageType: String? = null,
    @SerializedName("servings") var servings: Double? = null,
    @SerializedName("readyInMinutes") var readyInMinutes: Double? = null,
    @SerializedName("license") var license: String? = null,
    @SerializedName("sourceName") var sourceName: String? = null,
    @SerializedName("sourceUrl") var sourceUrl: String? = null,
    @SerializedName("spoonacularSourceUrl") var spoonacularSourceUrl: String? = null,
    @SerializedName("healthScore") var healthScore: Double? = null,
    @SerializedName("spoonacularScore") var spoonacularScore: Double? = null,
    @SerializedName("pricePerServing") var pricePerServing: Double? = null,
    @SerializedName("analyzedInstructions") var analyzedInstructions: ArrayList<AnalyzedInstructionDto> = arrayListOf(),
    @SerializedName("cheap") var cheap: Boolean? = null,
    @SerializedName("creditsText") var creditsText: String? = null,
    @SerializedName("cuisines") var cuisines: ArrayList<String> = arrayListOf(),
    @SerializedName("dairyFree") var dairyFree: Boolean? = null,
    @SerializedName("diets") var diets: ArrayList<String> = arrayListOf(),
    @SerializedName("gaps") var gaps: String? = null,
    @SerializedName("extendedIngredients") var extendedIngredients: ArrayList<ExtendedIngredientsDto> = arrayListOf(),
    @SerializedName("glutenFree") var glutenFree: Boolean? = null,
    @SerializedName("instructions") var instructions: String? = null,
    @SerializedName("ketogenic") var ketogenic: Boolean? = null,
    @SerializedName("lowFodmap") var lowFodmap: Boolean? = null,
    @SerializedName("occasions") var occasions: ArrayList<String> = arrayListOf(),
    @SerializedName("sustainable") var sustainable: Boolean? = null,
    @SerializedName("vegan") var vegan: Boolean? = null,
    @SerializedName("vegetarian") var vegetarian: Boolean? = null,
    @SerializedName("veryHealthy") var veryHealthy: Boolean? = null,
    @SerializedName("veryPopular") var veryPopular: Boolean? = null,
    @SerializedName("whole30") var whole30: Boolean? = null,
    @SerializedName("dishTypes") var dishTypes: ArrayList<String> = arrayListOf(),
    @SerializedName("summary") var summary: String? = null,
    @SerializedName("winePairing") var winePairing: WinePairingDto? = WinePairingDto(),

    var equipments: EquipmentsDto? = EquipmentsDto()
)