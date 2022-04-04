package com.tashuseyin.foodrecipesapp.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tashuseyin.foodrecipesapp.common.Constants.FAVORITE_RECIPES_TABLE
import com.tashuseyin.foodrecipesapp.data.model.FoodRecipes


@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var uid: Int,
    var result: FoodRecipes
)