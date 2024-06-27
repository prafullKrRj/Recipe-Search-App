package com.prafull.recipesearchapp.screens.search.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.prafull.recipesearchapp.ui.theme.orange

@Composable
fun NextScreenButton(modifier: Modifier = Modifier, to: String, onClick: () -> Unit) {
    Button(
            onClick = onClick,
            modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = orange
            )
    ) {
        Text(text = "Get $to >")
    }
}