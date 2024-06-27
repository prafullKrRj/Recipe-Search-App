package com.prafull.recipesearchapp

import android.app.Application
import com.prafull.recipesearchapp.auth.AuthViewModel
import com.prafull.recipesearchapp.data.local.FavouritesDao
import com.prafull.recipesearchapp.data.local.FavouritesDatabase
import com.prafull.recipesearchapp.data.remote.ApiService
import com.prafull.recipesearchapp.data.repos.FavouriteRepoImpl
import com.prafull.recipesearchapp.data.repos.HomeRepoImpl
import com.prafull.recipesearchapp.data.repos.RecipeRepoImpl
import com.prafull.recipesearchapp.data.repos.SearchRepoImpl
import com.prafull.recipesearchapp.domain.repos.FavouritesRepo
import com.prafull.recipesearchapp.domain.repos.HomeRepo
import com.prafull.recipesearchapp.domain.repos.RecipeRepository
import com.prafull.recipesearchapp.domain.repos.SearchRepo
import com.prafull.recipesearchapp.screens.favourites.FavViewModel
import com.prafull.recipesearchapp.screens.home.HomeViewModel
import com.prafull.recipesearchapp.screens.recipeScreen.RecipeViewModel
import com.prafull.recipesearchapp.screens.search.SearchViewModel
import com.prafull.recipesearchapp.utils.Const
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RecipeApp)
            modules(
                    module {
                        single<HomeRepo> {
                            HomeRepoImpl()
                        }
                        single<RecipeRepository> {
                            RecipeRepoImpl()
                        }
                        single<SearchRepo> {
                            SearchRepoImpl()
                        }
                        single<FavouritesRepo> { FavouriteRepoImpl(get()) }
                        single<ApiService> {
                            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                                .baseUrl(Const.BASE_URL).build().create(ApiService::class.java)
                        }
                        viewModel {
                            HomeViewModel()
                        }
                        viewModel {
                            RecipeViewModel(get())
                        }
                        viewModel {
                            SearchViewModel()
                        }
                        viewModel {
                            FavViewModel()
                        }
                        viewModel {
                            AuthViewModel()
                        }
                        single<FavouritesDatabase> {
                            FavouritesDatabase.getInstance(androidContext())
                        }
                        single<FavouritesDao> {
                            get<FavouritesDatabase>().favouritesDao()
                        }
                    }
            )
        }
    }
}