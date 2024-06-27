package com.prafull.recipesearchapp.screens.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prafull.recipesearchapp.domain.models.RecipeInfo
import com.prafull.recipesearchapp.screens.recipeScreen.Equipments
import com.prafull.recipesearchapp.screens.recipeScreen.InfoSection
import com.prafull.recipesearchapp.screens.search.SearchScreens
import com.prafull.recipesearchapp.screens.search.SearchViewModel

@Composable
fun FullRecipeContent(searchViewModel: SearchViewModel, recipe: RecipeInfo) {
    Column(Modifier.fillMaxSize()) {
        Row(
                Modifier
                    .fillMaxWidth()
                    .weight(.1f)
                    .padding(8.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Ingredients")
            IconButton(onClick = { searchViewModel.popTill(SearchScreens.Main) }) {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
        }
        Row(
                Modifier
                    .fillMaxWidth()
                    .weight(.1f)
                    .padding(8.dp), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Full Recipe")
            IconButton(onClick = { searchViewModel.popTill(SearchScreens.Ingredients) }) {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
        }
        LazyColumn(Modifier.weight(.7f), contentPadding = PaddingValues(12.dp)) {
            item {
                InfoSection(infoType = "Instructions", info = recipe.instructions)
            }
            item {
                if (recipe.equipments.isNotEmpty()) Equipments(equipments = recipe.equipments)
            }
            item {
                InfoSection(infoType = "Quick Summary", info = recipe.summary)
            }
        }
        NextScreenButton(Modifier.weight(.1f), to = "similar recipes") {
            searchViewModel.getSimilarRecipes()
            searchViewModel.updateList(SearchScreens.SimilarRecipe)
        }
    }
}