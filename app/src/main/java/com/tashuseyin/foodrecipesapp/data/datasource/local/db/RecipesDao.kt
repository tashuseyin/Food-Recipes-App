package com.tashuseyin.foodrecipesapp.data.datasource.local.db

import androidx.room.*
import com.tashuseyin.foodrecipesapp.data.datasource.local.entity.FavoritesEntity
import com.tashuseyin.foodrecipesapp.data.datasource.local.entity.FoodJokeEntity
import com.tashuseyin.foodrecipesapp.data.datasource.local.entity.RecipesEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: RecipesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipes(favoritesEntity: FavoritesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)

    @Query("SELECT * FROM recipes_table ORDER BY uid ASC")
    fun readRecipes(): Flow<List<RecipesEntity>>

    @Query("SELECT * FROM favorite_recipes_table ORDER By uid ASC")
    fun readFavoriteRecipes(): Flow<List<FavoritesEntity>>

    @Query("SELECT * FROM food_joke_table ORDER By uid ASC")
    fun readFoodJoke(): Flow<List<FoodJokeEntity>>

    @Delete
    suspend fun deleteFavoriteRecipe(favoritesEntity: FavoritesEntity)

    @Query("DELETE  FROM favorite_recipes_table")
    suspend fun deleteAllFavoriteRecipes()
}
