package com.tashuseyin.foodrecipesapp.data.datasource.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tashuseyin.foodrecipesapp.data.datasource.local.entity.FavoritesEntity
import com.tashuseyin.foodrecipesapp.data.datasource.local.entity.FoodJokeEntity
import com.tashuseyin.foodrecipesapp.data.datasource.local.entity.RecipesEntity

@Database(
    entities = [RecipesEntity::class, FavoritesEntity::class, FoodJokeEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipesDao

}