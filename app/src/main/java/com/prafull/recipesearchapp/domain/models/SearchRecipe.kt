package com.prafull.recipesearchapp.domain.models

data class SearchResults(
    val results: List<SearchRecipes> = emptyList()
)

data class SearchRecipes(
    val id: Int = 0,
    val title: String = "",
    val image: String = "",
    val imageType: String = ""
)