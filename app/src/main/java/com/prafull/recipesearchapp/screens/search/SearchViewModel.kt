package com.prafull.recipesearchapp.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.recipesearchapp.domain.models.RecipeInfo
import com.prafull.recipesearchapp.domain.models.SearchRecipes
import com.prafull.recipesearchapp.domain.models.SearchResults
import com.prafull.recipesearchapp.domain.models.SimilarRecipe
import com.prafull.recipesearchapp.domain.repos.FavouritesRepo
import com.prafull.recipesearchapp.domain.repos.RecipeRepository
import com.prafull.recipesearchapp.domain.repos.SearchRepo
import com.prafull.recipesearchapp.utils.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchViewModel : ViewModel(), KoinComponent {
    private val searchRepo by inject<SearchRepo>()
    private val recipeRepo by inject<RecipeRepository>()
    private val favouritesRepo by inject<FavouritesRepo>()

    private val _searchResults = MutableStateFlow(SearchUIState())
    val searchResults = _searchResults.asStateFlow()

    var query by mutableStateOf("")
    private val _selectedRecipe = MutableStateFlow(SheetUIState())
    val selectedRecipe = _selectedRecipe.asStateFlow()

    var showSheet by mutableStateOf(false)
    val list = mutableStateListOf<SearchScreens>()
    var isFavourite by mutableStateOf(false)

    init {
        list.add(SearchScreens.Main)
    }

    fun updateSelectedRecipe(searchResults: SearchRecipes) {
        isFavourite = false
        _selectedRecipe.update {
            it.copy(searchResults = searchResults, loading = true)
        }
        getRecipe()
    }

    fun getRecipe() {
        _selectedRecipe.update {
            it.copy(loading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            selectedRecipe.value.searchResults.id.let { id ->
                recipeRepo.getRecipe(id).collectLatest { networkResponse ->
                    when (networkResponse) {
                        is NetworkResponse.Error -> {
                            _selectedRecipe.update {
                                it.copy(error = true, loading = false)
                            }
                        }

                        NetworkResponse.Loading -> {
                            _selectedRecipe.update { it.copy(loading = true) }
                        }

                        is NetworkResponse.Success ->
                            networkResponse.data.let { recipe ->
                                _selectedRecipe.update {
                                    it.copy(recipe = recipe, loading = false)
                                }
                            }
                    }
                }
            }
        }
    }

    fun getResponses() {
        _searchResults.update {
            SearchUIState(loading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            searchRepo.getResponses(query = query).collectLatest { response ->
                when (response) {
                    is NetworkResponse.Error -> {
                        _searchResults.update { it.copy(error = true, loading = false) }
                    }

                    NetworkResponse.Loading -> {
                        _searchResults.update { it.copy(loading = true, error = false) }
                    }

                    is NetworkResponse.Success -> {
                        _searchResults.update {
                            it.copy(
                                    searchResults = response.data,
                                    loading = false,
                                    error = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun updateList(content: SearchScreens) {
        list.add(content)
    }

    fun toggleFavorite(recipe: RecipeInfo) {
        viewModelScope.launch {
            _selectedRecipe.update {
                it.copy(favourite = !it.favourite)
            }
            if (isFavourite) {
                isFavourite = false
                favouritesRepo.removeRecipe(recipe.id.toString())
            } else {
                isFavourite = true
                favouritesRepo.saveRecipe(recipe)
            }
        }
    }

    fun popTill(screen: SearchScreens) {
        while (list.isNotEmpty() && list.last() != screen) {
            list.removeLast()
        }
    }

    private val _similar = MutableStateFlow(SimilarUIState())
    val similar = _similar.asStateFlow()

    fun getSimilarRecipes() {
        _similar.update {
            it.copy(loading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            searchRepo.getSimilarRecipes(selectedRecipe.value.recipe.id).collectLatest { response ->
                when (response) {
                    is NetworkResponse.Error -> {
                        _similar.update {
                            it.copy(error = true, loading = false)
                        }
                    }

                    NetworkResponse.Loading -> {
                        _similar.update {
                            it.copy(loading = true)
                        }
                    }

                    is NetworkResponse.Success -> {
                        _similar.update {
                            it.copy(similarRecipes = response.data, loading = false)
                        }
                    }
                }
            }
        }
    }
}

data class SimilarUIState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val similarRecipes: List<SimilarRecipe> = emptyList()
)

data class SheetUIState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val searchResults: SearchRecipes = SearchRecipes(),
    val recipe: RecipeInfo = RecipeInfo(),
    val favourite: Boolean = false
)

data class SearchUIState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val searchResults: SearchResults = SearchResults()
)

enum class SearchScreens {
    Main, Ingredients, FullRecipe, SimilarRecipe
}