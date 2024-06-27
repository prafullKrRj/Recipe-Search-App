package com.prafull.recipesearchapp.screens.search.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.prafull.recipesearchapp.ui.theme.orange

@Composable
fun SheetDragHandle(
    modifier: Modifier = Modifier,
    title: String,
    onHide: () -> Unit,
    onFavoriteClick: () -> Unit,
    isFavorite: Boolean
) {
    Row(
            modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedIconButton(
                onClick = onHide,
                shape = CircleShape,
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier.weight(.15f)
        ) {
            Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Dismiss Sheet"
            )
        }
        Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(.7f)
        )
        IconButton(onClick = onFavoriteClick) {
            Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Favourites Icon",
                    tint = orange,
                    modifier = Modifier.weight(.15f)
            )
        }
    }
}