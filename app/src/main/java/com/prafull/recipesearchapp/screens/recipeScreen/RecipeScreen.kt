package com.prafull.recipesearchapp.screens.recipeScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import com.prafull.recipesearchapp.domain.models.Equipment
import com.prafull.recipesearchapp.screens.commons.ImageAndTextSection
import com.prafull.recipesearchapp.screens.commons.IngredientsSection
import com.prafull.recipesearchapp.screens.commons.RecipeImage
import com.prafull.recipesearchapp.screens.favourites.byteArrayToBitmap
import com.prafull.recipesearchapp.screens.home.ErrorScreen
import com.prafull.recipesearchapp.screens.home.Loader

@Composable
fun RecipeScreen(
    viewModel: RecipeViewModel
) {
    val context = LocalContext.current
    val state by viewModel.recipe.collectAsState()
    if (state.loading) {
        Loader()
    } else if (state.error) {
        ErrorScreen {
            viewModel.retry()
        }
    } else {
        LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item(key = "RecipeImage") {
                Box(Modifier.fillMaxWidth()) {
                    RecipeImage(data = if (state.recipe?.image?.isNotEmpty() == true) state.recipe!!.image else state.recipe?.imageByteArray?.let {
                        byteArrayToBitmap(
                                it
                        )
                    }, context = context)
                    Text(
                            text = state.recipe?.title ?: "Recipe",
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp),
                            color = Color(0xFFFFFFFF)
                    )
                }
            }
            item(key = "GeneralInfo") {
                Row {
                    GeneralInfoCard(
                            type = "Ready In",
                            value = "${state.recipe?.readyInMinutes} min",
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                    )
                    GeneralInfoCard(
                            type = "Servings",
                            value = "${state.recipe?.servings}",
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                    )
                    GeneralInfoCard(
                            type = "Price/Serving",
                            value = "${state.recipe?.pricePerServing}",
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                    )
                }
            }
            item(key = "Ingredients") {
                state.recipe?.ingredients?.let {
                    if (it.isNotEmpty()) IngredientsSection(
                            state.recipe?.ingredients ?: emptyList()
                    )
                }
            }
            item(key = "Instructions") {
                state.recipe?.instructions?.let {
                    if (it.isNotEmpty()) InfoSection("Instructions", it)
                }
            }
            item {
                state.recipe?.equipments?.let {
                    if (it.isNotEmpty()) Equipments(state.recipe?.equipments ?: emptyList())
                }
            }
            item {
                state.recipe?.summary?.let {
                    if (it.isNotEmpty()) InfoSection("Quick Summary", it)
                }
            }
        }
    }
}


@Composable
fun InfoSection(infoType: String, info: String) {
    Column(modifier = Modifier.padding(12.dp)) {
        Text(
                text = infoType,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(text = HtmlCompat.fromHtml(info, HtmlCompat.FROM_HTML_MODE_COMPACT).toString())
    }
}

@Composable
fun Equipments(equipments: List<Equipment>) {
    val context = LocalContext.current
    Column {
        Text(
                text = "Equipments",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                modifier = Modifier.padding(8.dp)
        )
        LazyRow(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(vertical = 8.dp)
        ) {
            items(equipments) { equipment ->
                ImageAndTextSection(
                        context = context,
                        name = equipment.name,
                        data = if (equipment.image.isNotEmpty()) "https://img.spoonacular.com/equipment_100x100/${equipment.image}"
                        else equipment.imageByteArray
                )
            }
        }
    }
}


@Composable
fun GeneralInfoCard(type: String, value: String, modifier: Modifier = Modifier) {
    OutlinedCard(modifier = modifier, border = BorderStroke(1.dp, Color.LightGray)) {
        Column(
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
        ) {
            Text(text = type, color = Color.Gray, fontSize = 14.sp)
            Text(
                    text = value,
                    color = Color(0xFFE54900),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
            )
        }
    }
}