package com.prafull.recipesearchapp.screens.favourites

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.prafull.recipesearchapp.screens.Routes

@Composable
fun FavScreen(favViewModel: FavViewModel, navController: NavController) {
    val favs by favViewModel.favourites.collectAsState()
    if (favs.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "\uD83D\uDE0A No Favourites")
        }
    } else {
        LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(8.dp)) {
            items(favs, key = {
                it.timeStamp
            }) { recipe ->
                val context = LocalContext.current
                OutlinedCard(
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
                                    navController.navigate(
                                            Routes.RecipeDetailsInternet(recipe.id.toInt())
                                    )
                                }, verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(byteArrayToBitmap(recipe.image)).build(),
                                contentDescription = "Recipe Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.weight(.3f)
                        )
                        Column(
                                verticalArrangement = Arrangement.spacedBy(
                                        4.dp, Alignment.CenterVertically
                                ), modifier = Modifier
                            .weight(.7f)
                            .padding(horizontal = 8.dp)
                        ) {
                            if (recipe.title.isNotEmpty()) {
                                Text(text = recipe.title, fontWeight = FontWeight.SemiBold)
                            }
                            if (recipe.readyInMinutes.isNotEmpty()) {
                                Text(text = "Ready in ${recipe.readyInMinutes} minutes")
                            }
                        }
                    }
                }
            }
        }
    }
    if (favViewModel.showDeleteDialog) {
        DeleteDialog(favViewModel)
    }
}

@Composable
fun DeleteDialog(favViewModel: FavViewModel) {
    AlertDialog(
            onDismissRequest = {
                favViewModel.showDeleteDialog = false
            },
            confirmButton = {
                TextButton(onClick = {
                    favViewModel.deleteAll()
                    favViewModel.showDeleteDialog = false
                }) {
                    Text(text = "Delete")
                }
            }, dismissButton = {
        TextButton(onClick = {
            favViewModel.showDeleteDialog = false
        }) {
            Text(text = "Cancel")
        }
    }, title = {
        Text(text = "Delete All recipes")
    },
            text = {
                Text(text = "Are you sure you want to delete all recipes?")
            }
    )
}

fun byteArrayToBitmap(byteArray: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}