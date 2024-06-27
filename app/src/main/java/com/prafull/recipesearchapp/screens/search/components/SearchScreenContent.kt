package com.prafull.recipesearchapp.screens.search.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.prafull.recipesearchapp.R
import com.prafull.recipesearchapp.domain.models.SearchRecipes
import com.prafull.recipesearchapp.screens.search.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreenContent(
    paddingValues: PaddingValues,
    results: List<SearchRecipes>,
    scope: CoroutineScope,
    searchViewModel: SearchViewModel,
    sheetState: SheetState
) {
    LazyColumn(Modifier.fillMaxSize(), contentPadding = paddingValues) {
        items(results, key = {
            it.id
        }) { result ->
            Row(
                    modifier = Modifier
                        .clickable {
                            scope.launch {
                                searchViewModel.updateSelectedRecipe(result)
                                searchViewModel.showSheet = true
                                sheetState.expand()
                            }
                        }
                        .padding(12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement
                        .spacedBy(8.dp)
            ) {
                Icon(
                        painter = painterResource(id = R.drawable.outline_fastfood_24),
                        contentDescription = "null",
                )
                Text(text = result.title)
            }
        }
    }
}