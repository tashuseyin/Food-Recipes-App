package com.tashuseyin.foodrecipesapp.util

import com.tashuseyin.foodrecipesapp.common.Constants

object Util {
    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries["number"] = "50"
        queries["apiKey"] = Constants.API_KEY
        queries["type"] = "snack"
        queries["diet"] = "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"
        return queries
    }
}