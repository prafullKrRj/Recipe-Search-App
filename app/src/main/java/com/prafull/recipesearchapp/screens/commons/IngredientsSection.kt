package com.prafull.recipesearchapp.screens.commons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prafull.recipesearchapp.domain.models.Ingredients


@Composable
fun IngredientsSection(ingredients: List<Ingredients>) {
    val context = LocalContext.current
    Column {
        Text(
                text = "Ingredients",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                modifier = Modifier.padding(8.dp)
        )
        LazyRow(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(vertical = 8.dp)
        ) {
            items(ingredients) { ingredients ->
                ImageAndTextSection(
                        context = context,
                        name = ingredients.name,
                        data = if (ingredients.image.isNotEmpty()) "https://img.spoonacular.com/ingredients_100x100/${ingredients.image}"
                        else ingredients.imageByteArray
                )
            }
        }
    }
}
