package com.prafull.recipesearchapp.screens.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.prafull.recipesearchapp.screens.commons.RecipeImage
import com.prafull.recipesearchapp.screens.home.ErrorScreen
import com.prafull.recipesearchapp.screens.home.Loader
import com.prafull.recipesearchapp.screens.recipeScreen.GeneralInfoCard
import com.prafull.recipesearchapp.screens.search.SearchScreens
import com.prafull.recipesearchapp.screens.search.SearchViewModel

@Composable
fun MainSheetContent(
    searchViewModel: SearchViewModel
) {
    val context = LocalContext.current
    val sheetUIState by searchViewModel.selectedRecipe.collectAsState()
    Column(
            Modifier.fillMaxSize()
    ) {
        LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(.9f),
                verticalArrangement = Arrangement.SpaceBetween
        ) {
            item {
                RecipeImage(data = sheetUIState.searchResults.image, context = context)
            }
            item {
                if (sheetUIState.loading) {
                    Loader()
                } else if (sheetUIState.error) {
                    ErrorScreen {
                        searchViewModel.getRecipe()
                    }
                } else {
                    Row(Modifier.padding(12.dp)) {
                        GeneralInfoCard(
                                type = "Ready In",
                                value = "${sheetUIState.recipe.readyInMinutes} min",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                        )
                        GeneralInfoCard(
                                type = "Servings",
                                value = "${sheetUIState.recipe.servings}",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                        )
                        GeneralInfoCard(
                                type = "Price/Serving",
                                value = sheetUIState.recipe.pricePerServing,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.dp)
                        )
                    }
                }
            }
        }
        NextScreenButton(Modifier.weight(.1f), to = "ingredients") {
            searchViewModel.updateList(SearchScreens.Ingredients)
        }
    }
}