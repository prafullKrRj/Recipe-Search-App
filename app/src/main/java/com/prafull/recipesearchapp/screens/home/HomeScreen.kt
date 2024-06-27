package com.prafull.recipesearchapp.screens.home

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.prafull.recipesearchapp.R
import com.prafull.recipesearchapp.domain.models.RecipeInfo
import com.prafull.recipesearchapp.screens.Routes
import com.prafull.recipesearchapp.ui.theme.searchFieldColor
import java.util.UUID

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavController,
) {
    val popularRecipes by homeViewModel.popularRecipes.collectAsState()
    val allRecipes by homeViewModel.allRecipes.collectAsState()
    LazyColumn(
            Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            HomeSearchField {
                navController.navigate(Routes.Search)
            }
        }
        item {
            Text(
                    text = stringResource(R.string.popular_recipes),
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        item {
            if (popularRecipes.loading) {
                Loader(modifier = Modifier.height(200.dp))
            } else if (popularRecipes.error) {
                ErrorScreen {
                    homeViewModel.getPopularRecipes()
                }
            } else {
                PopularRecipes(recipes = popularRecipes.data, navController = navController)
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text(
                    text = stringResource(R.string.all_recipes),
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
        if (allRecipes.loading) {
            item { Loader() }
        }
        if (allRecipes.error) {
            item { ErrorScreen { homeViewModel.getAllRecipes() } }
        }
        items(allRecipes.data, key = {
            it.id ?: UUID.randomUUID().toString()
        }) { recipe ->
            FullWidthRecipeCard(recipe) {
                recipe.id.let { id ->
                    navController.navigate(Routes.RecipeDetailsInternet(id))
                }
            }
        }
    }
}

@Composable
fun PopularRecipes(recipes: List<RecipeInfo>, navController: NavController) {
    val context = LocalContext.current
    LazyRow(
            verticalAlignment = Alignment.CenterVertically,
    ) {
        items(recipes, key = {
            it.id ?: UUID.randomUUID().toString()
        }) { recipe ->
            PopularRecipeCard(recipe = recipe, context = context) {
                recipe.id.let { id ->
                    navController.navigate(Routes.RecipeDetailsInternet(id.toInt()))
                }
            }
        }
    }
}

@Composable
fun ErrorScreen(retry: () -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        Text(text = "An error occurred", color = Color.Red)
        Button(onClick = retry) {
            Text(text = "Retry")
        }
    }
}


@Composable
fun Loader(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullWidthRecipeCard(recipe: RecipeInfo, onClick: () -> Unit = {}) {
    val context = LocalContext.current
    OutlinedCard(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            border = BorderStroke(1.dp, Color.LightGray),
    ) {
        Row(
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick()
                    }, verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                    model = ImageRequest.Builder(context).data(recipe.image).build(),
                    contentDescription = "Recipe Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.weight(.3f)
            )
            Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                    modifier = Modifier
                        .weight(.7f)
                        .padding(horizontal = 8.dp)
            ) {
                recipe.title?.let {
                    Text(text = it, fontWeight = FontWeight.SemiBold)
                }
                recipe.readyInMinutes?.let {
                    Text(text = "Ready in $it minutes")
                }
            }
        }
    }
}

@Composable
fun PopularRecipeCard(recipe: RecipeInfo, context: Context, onClick: () -> Unit = {}) {
    Card(
            modifier = Modifier
                .padding(8.dp)
                .height(220.dp)
                .width(200.dp)
    ) {
        Box(
                Modifier
                    .fillMaxSize()
                    .clickable {
                        onClick()
                    }) {
            AsyncImage(
                    model = ImageRequest.Builder(context).data(recipe.image).build(),
                    contentDescription = "Recipe Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
            )
            Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                                brush = Brush.verticalGradient(
                                        colors = listOf(
                                                Color.Transparent,
                                                Color.Black
                                        )
                                )
                        )
            )
            Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp), modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
            ) {
                recipe.title?.let {
                    Text(
                            text = it,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                    )
                }
                recipe.readyInMinutes?.let {
                    Text(
                            text = "Ready in $it minutes",
                            color = Color.LightGray
                    )
                }
            }
        }
    }
}

@Composable
fun HomeSearchField(onClick: () -> Unit) {
    OutlinedTextField(value = "", onValueChange = {}, modifier = Modifier
        .clickable {
            onClick()
        }
        .fillMaxWidth()
        .padding(horizontal = 16.dp), enabled = false, colors = TextFieldDefaults.colors(
            disabledContainerColor = searchFieldColor,
            focusedContainerColor = searchFieldColor,
            unfocusedIndicatorColor = searchFieldColor,
            disabledLabelColor = Color.Black,
            disabledLeadingIconColor = Color.Black
    ), shape = RoundedCornerShape(8.dp), label = {
        Text(text = "Search any recipe")
    }, leadingIcon = {
        Icon(imageVector = Icons.Default.Search, contentDescription = "NULL")
    })
}