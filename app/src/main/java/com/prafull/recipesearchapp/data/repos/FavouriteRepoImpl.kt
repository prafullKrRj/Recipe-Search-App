package com.prafull.recipesearchapp.data.repos

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import coil.ImageLoader
import coil.request.ImageRequest
import com.prafull.recipesearchapp.data.local.FavouritesDao
import com.prafull.recipesearchapp.data.local.RecipeEntity
import com.prafull.recipesearchapp.domain.models.RecipeInfo
import com.prafull.recipesearchapp.domain.repos.FavouritesRepo
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.ByteArrayOutputStream

class FavouriteRepoImpl(private val context: Context) : FavouritesRepo, KoinComponent {
    private val favouritesDao by inject<FavouritesDao>()
    override fun getFavouriteRecipes(): Flow<List<RecipeEntity>> {
        return favouritesDao.getAllRecipes()
    }

    override suspend fun removeRecipe(recipeId: String) = favouritesDao.deleteFromId(recipeId)
    override suspend fun deleteAll(): Boolean {
        try {
            favouritesDao.deleteAll()
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override fun getRecipe(id: Int): Flow<RecipeEntity?> {
        return favouritesDao.getRecipe(id)
    }

    override suspend fun saveRecipe(recipeInfo: RecipeInfo): Long {
        val imageLoader = ImageLoader(context)

        // Convert main image to byte array
        val mainImageRequest = ImageRequest.Builder(context)
            .data(recipeInfo.image)
            .build()
        val mainDrawable = imageLoader.execute(mainImageRequest).drawable
        if (mainDrawable is BitmapDrawable) {
            val mainBitmap = mainDrawable.bitmap
            val mainOutputStream = ByteArrayOutputStream()
            mainBitmap.compress(Bitmap.CompressFormat.PNG, 100, mainOutputStream)
            val mainByteArray = mainOutputStream.toByteArray()

            // Convert images in ingredients and equipments to byte arrays
            recipeInfo.ingredients.forEach { ingredient ->
                val ingredientImageRequest = ImageRequest.Builder(context)
                    .data(ingredient.image)
                    .build()
                val ingredientDrawable = imageLoader.execute(ingredientImageRequest).drawable
                if (ingredientDrawable is BitmapDrawable) {
                    val ingredientBitmap = ingredientDrawable.bitmap
                    val ingredientOutputStream = ByteArrayOutputStream()
                    ingredientBitmap.compress(
                            Bitmap.CompressFormat.PNG,
                            100,
                            ingredientOutputStream
                    )
                    ingredient.imageByteArray = ingredientOutputStream.toByteArray()
                    ingredient.image = ""
                }
            }

            recipeInfo.equipments.forEach { equipment ->
                val equipmentImageRequest = ImageRequest.Builder(context)
                    .data(equipment.image)
                    .build()
                val equipmentDrawable = imageLoader.execute(equipmentImageRequest).drawable
                if (equipmentDrawable is BitmapDrawable) {
                    val equipmentBitmap = equipmentDrawable.bitmap
                    val equipmentOutputStream = ByteArrayOutputStream()
                    equipmentBitmap.compress(Bitmap.CompressFormat.PNG, 100, equipmentOutputStream)
                    equipment.imageByteArray = equipmentOutputStream.toByteArray()
                    equipment.image = ""
                }
            }

            val recipeEntity = RecipeEntity(
                    id = recipeInfo.id.toString(),
                    title = recipeInfo.title,
                    image = mainByteArray,
                    readyInMinutes = recipeInfo.readyInMinutes,
                    pricePerServing = recipeInfo.pricePerServing,
                    servings = recipeInfo.servings,
                    instructions = recipeInfo.instructions,
                    summary = recipeInfo.summary,
                    ingredients = recipeInfo.ingredients,
                    equipments = recipeInfo.equipments,
                    nutrition = recipeInfo.nutrition,
                    similarRecipes = recipeInfo.similarRecipes
            )
            return favouritesDao.insertRecipe(recipeEntity)
        } else {
            return 0
        }
    }
}