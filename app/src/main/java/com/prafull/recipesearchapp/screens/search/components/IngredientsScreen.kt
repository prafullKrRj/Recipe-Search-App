package com.prafull.recipesearchapp.screens.search.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prafull.recipesearchapp.domain.models.Ingredients
import com.prafull.recipesearchapp.screens.commons.ImageAndTextSection
import com.prafull.recipesearchapp.screens.search.SearchScreens
import com.prafull.recipesearchapp.screens.search.SearchViewModel
import java.util.UUID

@Composable
fun IngredientsScreen(
    searchViewModel: SearchViewModel,
    ingredients: List<Ingredients>,
    backButtonClicked: () -> Unit
) {
    val context = LocalContext.current
    BackHandler {
        backButtonClicked()
    }
    Column(
            Modifier.fillMaxSize()
    ) {
        Row(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .weight(.1f),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Ingredients", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            IconButton(onClick = backButtonClicked) {
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Back Button")
            }
        }
        LazyVerticalGrid(
                columns = GridCells.Adaptive(100.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(16.dp),
                modifier = Modifier.weight(.8f)
        ) {
            items(ingredients, key = {
                UUID.randomUUID().toString()
            }) { ingredient ->
                if (ingredient.image.isNotEmpty() && ingredient.name.isNotEmpty()) {
                    ImageAndTextSection(
                            context = context,
                            name = ingredient.name,
                            data = "https://img.spoonacular.com/ingredients_100x100/${ingredient.image}"
                    )
                }
            }
            if (ingredients.isEmpty()) {
                item {
                    Text(text = "No ingredients available")
                }
            }
        }
        NextScreenButton(Modifier.weight(.1f), to = "full recipe") {
            searchViewModel.updateList(SearchScreens.FullRecipe)
        }
    }
}