package com.tashuseyin.foodrecipesapp.data.datasource.remote

import com.tashuseyin.foodrecipesapp.data.model.remote.FoodResult
import retrofit2.Response

interface RemoteDataSourceImpl {
    suspend fun getFoodRecipes(queries: Map<String, String>): Response<FoodResult>
}
