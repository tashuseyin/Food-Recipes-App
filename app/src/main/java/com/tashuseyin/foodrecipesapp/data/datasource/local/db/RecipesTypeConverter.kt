package com.tashuseyin.foodrecipesapp.data.datasource.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tashuseyin.foodrecipesapp.data.model.FoodResult

class RecipesTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodResult: FoodResult): String {
        return gson.toJson(foodResult)
    }

    @TypeConverter
    fun stringToFoodRecipe(data: String): FoodResult {
        val listType = object : TypeToken<FoodResult>() {}.type
        return gson.fromJson(data, listType)
    }
}