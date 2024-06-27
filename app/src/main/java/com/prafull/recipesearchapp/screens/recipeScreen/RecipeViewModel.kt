package com.prafull.recipesearchapp.screens.recipeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.recipesearchapp.domain.models.RecipeInfo
import com.prafull.recipesearchapp.domain.repos.FavouritesRepo
import com.prafull.recipesearchapp.domain.repos.RecipeRepository
import com.prafull.recipesearchapp.utils.NetworkResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RecipeViewModel(
    private val id: Int
) : ViewModel(), KoinComponent {
    private val favouritesRepo by inject<FavouritesRepo>()

    private val repository: RecipeRepository by inject()
    private val _recipe = MutableStateFlow(RecipeUiState())
    val recipe = _recipe.asStateFlow()

    init {
        getRecipeDetails()
    }

    fun getRecipeDetails() {
        _recipe.update {
            RecipeUiState(loading = true)
        }
        viewModelScope.launch {
            favouritesRepo.getRecipe(id).collect { entity ->
                if (entity == null) {
                    repository.getRecipe(id).collect { recipeResponse ->
                        when (recipeResponse) {
                            is NetworkResponse.Error -> _recipe.update {
                                RecipeUiState(
                                        error = true,
                                        loading = false
                                )
                            }

                            NetworkResponse.Loading -> _recipe.update {
                                RecipeUiState(
                                        loading = true,
                                        error = false
                                )
                            }

                            is NetworkResponse.Success -> _recipe.update {
                                RecipeUiState(
                                        recipe = recipeResponse.data,
                                        loading = false,
                                        error = false
                                )
                            }
                        }
                    }
                } else {
                    _recipe.update {
                        RecipeUiState(recipe = entity.toRecipeInfo())
                    }
                }
            }
        }
    }

    fun retry() = getRecipeDetails()
}

data class RecipeUiState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val recipe: RecipeInfo? = null,
)