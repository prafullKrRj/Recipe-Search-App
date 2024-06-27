package com.prafull.recipesearchapp.domain.mappers

import com.prafull.recipesearchapp.data.dtos.searchRecipesDto.SearchRecipesDto
import com.prafull.recipesearchapp.data.dtos.searchRecipesDto.SearchResultsDto
import com.prafull.recipesearchapp.domain.models.SearchRecipes
import com.prafull.recipesearchapp.domain.models.SearchResults

fun SearchResultsDto.toSearchResults(): SearchResults {
    return SearchResults(
            results = results.map {
                it.toSearchRecipes()
            }
    )
}

fun SearchRecipesDto.toSearchRecipes(): SearchRecipes {
    return SearchRecipes(
            id = id ?: 0,
            title = title ?: "",
            image = image ?: "",
            imageType = imageType ?: ""
    )
}