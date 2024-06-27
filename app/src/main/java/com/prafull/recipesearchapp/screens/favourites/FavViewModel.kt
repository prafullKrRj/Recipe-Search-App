package com.prafull.recipesearchapp.screens.favourites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.recipesearchapp.data.local.RecipeEntity
import com.prafull.recipesearchapp.domain.repos.FavouritesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FavViewModel : ViewModel(), KoinComponent {

    var showDeleteDialog: Boolean by mutableStateOf(false)
    private val favouritesRepo by inject<FavouritesRepo>()

    val favourites: StateFlow<List<RecipeEntity>> = favouritesRepo.getFavouriteRecipes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            if (favouritesRepo.deleteAll()) {
                showDeleteDialog = false
            }
        }
    }
}