package com.prafull.recipesearchapp.screens.commons

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun RecipeImage(data: Any?, context: Context) {
    AsyncImage(
            model = ImageRequest.Builder(context).data(data)
                .build(),
            contentDescription = "Recipe Image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                        brush = Brush.verticalGradient(
                                colors = listOf(
                                        Color.Transparent,
                                        Color.Black
                                )
                        )
                )
    )
}