package com.prafull.recipesearchapp.screens.search

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafull.recipesearchapp.screens.Routes
import com.prafull.recipesearchapp.screens.goBackStack
import com.prafull.recipesearchapp.screens.home.ErrorScreen
import com.prafull.recipesearchapp.screens.home.Loader
import com.prafull.recipesearchapp.screens.search.components.FullRecipeContent
import com.prafull.recipesearchapp.screens.search.components.IngredientsScreen
import com.prafull.recipesearchapp.screens.search.components.MainSheetContent
import com.prafull.recipesearchapp.screens.search.components.SearchScreenContent
import com.prafull.recipesearchapp.screens.search.components.SheetDragHandle
import com.prafull.recipesearchapp.ui.theme.searchFieldColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel,
    navController: NavController,
    focusRequester: FocusRequester
) {
    val state by searchViewModel.searchResults.collectAsState()
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true,
            confirmValueChange = {
                it != SheetValue.Hidden
            }
    )
    BackHandler {
        scope.launch {
            searchViewModel.showSheet = false
            sheetState.hide()
        }
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    LaunchedEffect(key1 = searchViewModel.query) {
        searchViewModel.getResponses()
    }
    val selectedRecipe by searchViewModel.selectedRecipe.collectAsState()
    LaunchedEffect(key1 = searchViewModel.list.size) {
        Log.d("SearchScreen", "List size: ${searchViewModel.list}")
    }
    if (searchViewModel.showSheet) {
        ModalBottomSheet(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.9f),
                sheetState = sheetState,
                onDismissRequest = {
                    if (searchViewModel.list.size <= 1) {
                        scope.launch {
                            sheetState.hide()
                            searchViewModel.showSheet = false
                        }
                    } else {
                        searchViewModel.list.removeLast()
                    }
                }
        ) {
            SheetContent(
                    searchViewModel = searchViewModel,
                    selectedRecipe = selectedRecipe,
                    sheetState = sheetState,
                    scope = scope,
                    navController
            )
        }
    }
    Scaffold(
            topBar = {
                SearchTopAppBar(
                        searchViewModel = searchViewModel,
                        navController = navController,
                        focusRequester
                )
            }
    ) { paddingValues ->
        if (state.loading) {
            Loader()
        } else if (state.error) {
            ErrorScreen {
                searchViewModel.getResponses()
            }
        } else {
            SearchScreenContent(
                    paddingValues = paddingValues,
                    results = state.searchResults.results,
                    scope = scope,
                    searchViewModel = searchViewModel,
                    sheetState = sheetState
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetContent(
    searchViewModel: SearchViewModel,
    selectedRecipe: SheetUIState,
    sheetState: SheetState,
    scope: CoroutineScope,
    navController: NavController
) {
    Column(
            Modifier.fillMaxSize()
    ) {
        SheetDragHandle(
                modifier = Modifier,
                title = selectedRecipe.recipe.title,
                isFavorite = searchViewModel.isFavourite,
                onHide = {
                    scope.launch {
                        searchViewModel.popTill(SearchScreens.Main)
                        searchViewModel.showSheet = false
                        sheetState.hide()
                    }
                },
                onFavoriteClick = {
                    searchViewModel.toggleFavorite(selectedRecipe.recipe)
                },
        )
        Column(
                Modifier.fillMaxSize()
        ) {
            when (searchViewModel.list.last()) {
                SearchScreens.Main -> {
                    MainSheetContent(searchViewModel = searchViewModel)
                }

                SearchScreens.Ingredients -> {
                    IngredientsScreen(searchViewModel, selectedRecipe.recipe.ingredients) {
                        searchViewModel.list.removeLast()
                    }
                }

                SearchScreens.FullRecipe -> {
                    FullRecipeContent(searchViewModel = searchViewModel, selectedRecipe.recipe)
                }

                SearchScreens.SimilarRecipe -> {
                    SimilarContent(searchViewModel, navController = navController)
                }
            }
        }
    }
}

@Composable
fun SimilarContent(searchViewModel: SearchViewModel, navController: NavController) {
    val similar by searchViewModel.similar.collectAsState()
    val context = LocalContext.current
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        SheetContentTypeSection(modifier = Modifier.weight(.1f), text = "Ingredients") {
            searchViewModel.popTill(SearchScreens.Main)
        }
        SheetContentTypeSection(modifier = Modifier.weight(.1f), text = "Full Recipe") {
            searchViewModel.popTill(SearchScreens.Ingredients)
        }
        SheetContentTypeSection(modifier = Modifier.weight(.1f), text = "Similar Recipe") {
            searchViewModel.popTill(SearchScreens.FullRecipe)
        }
        Column(Modifier.weight(.7f)) {
            if (similar.loading) {
                Loader()
            } else if (similar.error) {
                ErrorScreen {
                    searchViewModel.getSimilarRecipes()
                }
            } else {
                LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(8.dp)) {
                    items(similar.similarRecipes) {
                        Row(
                                Modifier
                                    .fillMaxWidth()
                                    .border(1.dp, Color.Gray)
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable {
                                        navController.navigate(Routes.RecipeDetailsInternet(it.id))
                                    }, verticalAlignment = Alignment.CenterVertically
                        ) {
                            /*      AsyncImage(
                                          model = ImageRequest.Builder(context).data(it.image).build(),
                                          contentDescription = "Recipe Image",
                                          contentScale = ContentScale.Crop,
                                          modifier = Modifier.weight(.3f)
                                  )*/
                            Column(
                                    verticalArrangement = Arrangement.spacedBy(
                                            4.dp, Alignment.CenterVertically
                                    ), modifier = Modifier
                                .weight(.7f)
                                .padding(horizontal = 8.dp)
                            ) {
                                if (it.title.isNotEmpty()) {
                                    Text(text = it.title, fontWeight = FontWeight.SemiBold)
                                }
                                if (it.readyInMinutes.isNotEmpty()) {
                                    Text(text = "Ready in ${it.readyInMinutes} minutes")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SheetContentTypeSection(modifier: Modifier = Modifier, text: String, iconClicked: () -> Unit) {
    Row(
            modifier
                .fillMaxWidth()
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = text)
        IconButton(onClick = iconClicked) {
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTopAppBar(
    searchViewModel: SearchViewModel,
    navController: NavController,
    focusRequester: FocusRequester
) {
    TopAppBar(
            title = {
                OutlinedTextField(
                        singleLine = true, value = searchViewModel.query,
                        onValueChange = {
                            searchViewModel.query = it
                        },
                        leadingIcon = {
                            IconButton(onClick = { navController.goBackStack() }) {
                                Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Back to Home"
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .padding(horizontal = 8.dp),
                        trailingIcon = {
                            if (searchViewModel.query.isNotEmpty()) {
                                IconButton(onClick = {
                                    searchViewModel.query = ""
                                }) {
                                    Icon(
                                            imageVector = Icons.Default.Clear,
                                            contentDescription = "Clear"
                                    )
                                }
                            }
                        },
                        label = {
                            Text(text = "Search")
                        },
                        colors = TextFieldDefaults.colors(
                                focusedContainerColor = searchFieldColor,
                                unfocusedContainerColor = searchFieldColor,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedIndicatorColor = searchFieldColor,
                                unfocusedIndicatorColor = searchFieldColor,
                        ),
                        shape = RoundedCornerShape(8.dp),
                        textStyle = TextStyle(
                                fontSize = 14.sp
                        )
                )
            },
    )
}