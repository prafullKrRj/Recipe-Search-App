package com.prafull.recipesearchapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.initialize
import com.prafull.recipesearchapp.auth.AuthScreen
import com.prafull.recipesearchapp.screens.MainNavigation
import com.prafull.recipesearchapp.ui.theme.RecipeSearchAppTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Firebase.initialize(this)
        val firebaseAuth = FirebaseAuth.getInstance()
        setContent {
            RecipeSearchAppTheme {
                val navController = rememberNavController()
                val destination = if (firebaseAuth.currentUser == null) {
                    MainScreens.AuthScreen
                } else {
                    MainScreens.MainApp
                }
                Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .systemBarsPadding()
                ) {
                    NavHost(navController = navController, startDestination = destination) {
                        composable<MainScreens.AuthScreen> {
                            AuthScreen(navController, firebaseAuth)
                        }
                        composable<MainScreens.MainApp> {
                            MainNavigation(navController, firebaseAuth)
                        }
                    }
                }
            }
        }
    }
}

sealed interface MainScreens {
    @Serializable
    data object AuthScreen : MainScreens

    @Serializable
    data object MainApp : MainScreens
}

fun NavController.navigateAndPopBackStack(route: Any) {
    popBackStack()
    navigate(route)
}
