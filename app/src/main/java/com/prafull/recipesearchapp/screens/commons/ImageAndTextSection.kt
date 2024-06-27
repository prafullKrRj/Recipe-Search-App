package com.prafull.recipesearchapp.screens.commons

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.util.Locale

@Composable
fun ImageAndTextSection(context: Context, name: String, data: Any?) {
    Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
    ) {
        AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(data)
                    .build(),
                contentDescription = "Ingredient Image",
                modifier = Modifier
                    .size(100.dp)
                    .border(1.dp, Color.LightGray, CircleShape)
                    .clip(CircleShape)
        )
        Text(text = name.capitalize(Locale.getDefault()))
    }
}
