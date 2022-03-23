package com.tashuseyin.foodrecipesapp.data.datasource.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tashuseyin.foodrecipesapp.common.Constants
import com.tashuseyin.foodrecipesapp.data.model.FoodRecipes
import com.tashuseyin.foodrecipesapp.data.model.FoodResult

@Entity(tableName = Constants.RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodResult
) {
    @PrimaryKey(autoGenerate = false)
    var uid: Int = 0
}