package com.prafull.recipesearchapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.google.firebase.auth.FirebaseAuth
import com.prafull.recipesearchapp.R
import com.prafull.recipesearchapp.screens.favourites.FavScreen
import com.prafull.recipesearchapp.screens.favourites.FavViewModel
import com.prafull.recipesearchapp.screens.home.HomeScreen
import com.prafull.recipesearchapp.screens.home.HomeViewModel
import com.prafull.recipesearchapp.screens.recipeScreen.RecipeScreen
import com.prafull.recipesearchapp.screens.recipeScreen.RecipeViewModel
import com.prafull.recipesearchapp.screens.search.SearchScreen
import com.prafull.recipesearchapp.screens.search.SearchViewModel
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MainNavigation(mainNavController: NavController, auth: FirebaseAuth) {

    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = koinViewModel()
    val favViewModel: FavViewModel = koinViewModel()

    val currDestination by navController.currentBackStackEntryFlow.collectAsState(initial = Routes.Home)
    val currScreen = if (currDestination.toString()
            .contains("Home")
    ) Screens.Home.name else if (currDestination.toString()
            .contains("Fav")
    ) Screens.Fav.name else if (currDestination.toString()
            .contains("Search")
    ) Screens.Search.name
    else Screens.RecipeDetails.name
    val focusRequester = remember {
        FocusRequester()
    }
    Scaffold(
            topBar = {
                when (currScreen) {
                    Screens.Home.name -> HomeTopAppBar(auth, mainNavController)
                    Screens.Fav.name -> FavTopAppBar(favViewModel)
                    Screens.RecipeDetails.name -> RecipeTopAppBar(navController = navController)
                }
            },
            bottomBar = {
                if (!currDestination.toString().contains("Search") && !currDestination.toString()
                        .contains("RecipeDetails")
                ) {
                    NavigationBar {
                        NavigationBarItem(
                                selected = currScreen == Screens.Home.name,
                                onClick = {
                                    navController.navigateAndPopBackStack(Routes.Home)
                                },
                                icon = {
                                    val icon =
                                        if (currScreen == Screens.Home.name) Icons.Default.Home else Icons.Outlined.Home
                                    Icon(imageVector = icon, contentDescription = "Home")
                                })
                        NavigationBarItem(selected = currScreen == Screens.Fav.name, onClick = {
                            navController.navigateAndPopBackStack(Routes.Fav)
                        }, icon = {
                            val icon =
                                if (currScreen == Screens.Fav.name) Icons.Default.Favorite else Icons.Default.FavoriteBorder
                            Icon(imageVector = icon, contentDescription = "Home")
                        })
                    }
                }
            }
    ) { paddingValues ->
        NavHost(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                navController = navController,
                startDestination = Routes.Home
        ) {
            composable<Routes.Home> {
                HomeScreen(homeViewModel = homeViewModel, navController = navController)
            }
            composable<Routes.Fav> {
                FavScreen(favViewModel, navController)
            }
            composable<Routes.Search> {
                val searchViewModel = koinViewModel<SearchViewModel>()
                SearchScreen(
                        searchViewModel = searchViewModel,
                        navController = navController,
                        focusRequester
                )
            }
            composable<Routes.RecipeDetailsInternet> {
                val information = it.toRoute<Routes.RecipeDetailsInternet>()
                val recipeViewModel =
                    koinViewModel<RecipeViewModel> { parametersOf(information.recipeId) }
                RecipeScreen(recipeViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeTopAppBar(navController: NavController) {
    TopAppBar(title = {
        Text(text = "Recipe Details")
    }, navigationIcon = {
        IconButton(onClick = {
            navController.goBackStack()
        }) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(mAuth: FirebaseAuth, mainNavController: NavController) {
    val userName = mAuth.currentUser?.displayName ?: "Master Chef"
    val context = LocalContext.current
    var showLogoutDialog by remember {
        mutableStateOf(false)
    }
    TopAppBar(title = {
        Row {
            Column {
                Text(text = "👋 Hey $userName")
                Text(
                        text = "Discover tasty and healthy recipes",
                        fontSize = 16.sp,
                        color = Color.Gray
                )
            }
        }
    }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavTopAppBar(favViewModel: FavViewModel) {
    TopAppBar(title = {
        Row {
            Text(text = stringResource(id = R.string.favourite_recipes))
        }
    }, actions = {
        IconButton(onClick = {
            favViewModel.showDeleteDialog = true
        }) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
        }
    })
}

sealed interface Routes {
    @Serializable
    data object Home : Routes


    @Serializable
    data object Fav : Routes

    @Serializable
    data object Search : Routes

    @Serializable
    data class RecipeDetailsInternet(val recipeId: Int) : Routes

}

enum class Screens {
    Home, Fav, Search, RecipeDetails
}

fun NavController.navigateAndPopBackStack(route: Any) {
    popBackStack()
    navigate(route)
}

fun NavController.goBackStack() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}