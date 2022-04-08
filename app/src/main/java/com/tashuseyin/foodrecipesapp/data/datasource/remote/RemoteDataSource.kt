package com.tashuseyin.foodrecipesapp.data.datasource.remote

import com.tashuseyin.foodrecipesapp.data.datasource.remote.api.FoodRecipesApi
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {
    suspend fun getFoodRecipes(queries: Map<String, String>) =
        foodRecipesApi.getRecipes(queries)

    suspend fun searchRecipes(queries: Map<String, String>) =
        foodRecipesApi.searchRecipes(queries)

    suspend fun getFoodJoke(apiKey: String) =
        foodRecipesApi.getFoodJoke(apiKey)
}