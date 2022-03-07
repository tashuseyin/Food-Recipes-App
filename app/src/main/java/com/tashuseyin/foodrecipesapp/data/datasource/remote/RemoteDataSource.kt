package com.tashuseyin.foodrecipesapp.data.datasource.remote

import com.tashuseyin.foodrecipesapp.data.datasource.remote.api.FoodRecipesApi
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) : RemoteDataSourceImpl {
    override suspend fun getFoodRecipes(queries: Map<String, String>) =
        foodRecipesApi.getRecipes(queries)
}