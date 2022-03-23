package com.tashuseyin.foodrecipesapp.util

import com.tashuseyin.foodrecipesapp.common.Constants.API_KEY
import com.tashuseyin.foodrecipesapp.common.Constants.QUERY_ADD_RECIPE_INFORMATION
import com.tashuseyin.foodrecipesapp.common.Constants.QUERY_API_KEY
import com.tashuseyin.foodrecipesapp.common.Constants.QUERY_DIET
import com.tashuseyin.foodrecipesapp.common.Constants.QUERY_FILL_INGREDIENTS
import com.tashuseyin.foodrecipesapp.common.Constants.QUERY_NUMBER
import com.tashuseyin.foodrecipesapp.common.Constants.QUERY_TYPE

object Util {
    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = "main course"
        queries[QUERY_DIET] = "gluten free"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }
}