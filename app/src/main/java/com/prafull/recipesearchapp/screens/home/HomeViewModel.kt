package com.prafull.recipesearchapp.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.recipesearchapp.domain.models.RecipeInfo
import com.prafull.recipesearchapp.domain.repos.HomeRepo
import com.prafull.recipesearchapp.utils.NetworkResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeViewModel : ViewModel(), KoinComponent {
    private val homeRepo: HomeRepo by inject()

    private val _popularRecipes = MutableStateFlow(PopularRecipeUI(loading = true))
    val popularRecipes = _popularRecipes.asStateFlow()

    private val _allRecipes = MutableStateFlow(PopularRecipeUI(loading = true))
    val allRecipes = _popularRecipes.asStateFlow()

    init {
        getPopularRecipes()
        getAllRecipes()
    }

    fun getPopularRecipes() {
        _popularRecipes.update {
            PopularRecipeUI(loading = true, error = false)
        }
        viewModelScope.launch {
            homeRepo.getRandomRecipes().collect { response ->
                when (response) {
                    is NetworkResponse.Error -> {
                        _popularRecipes.update { it.copy(loading = false, error = true) }
                    }

                    is NetworkResponse.Loading -> {
                        _popularRecipes.update { it.copy(loading = true, error = false) }
                    }

                    is NetworkResponse.Success -> {
                        _popularRecipes.update {
                            it.copy(loading = false, data = response.data)
                        }
                    }
                }
            }
        }
    }

    fun getAllRecipes() {
        _popularRecipes.update {
            PopularRecipeUI(loading = true, error = false)
        }
        viewModelScope.launch {
            homeRepo.getRandomRecipes().collect { response ->
                when (response) {
                    is NetworkResponse.Error -> {
                        _popularRecipes.update { it.copy(loading = false, error = true) }
                    }

                    is NetworkResponse.Loading -> {
                        _popularRecipes.update { it.copy(loading = true, error = false) }
                    }

                    is NetworkResponse.Success -> {
                        _popularRecipes.update {
                            it.copy(loading = false, data = response.data)
                        }
                    }
                }
            }
        }
    }

    fun retryPop() = getPopularRecipes()
    fun retryAll() = getAllRecipes()
}

data class PopularRecipeUI(
    val loading: Boolean = false,
    val error: Boolean = false,
    val data: List<RecipeInfo> = emptyList()
)