package com.tashuseyin.foodrecipesapp.data.datasource.remote.api

import com.tashuseyin.foodrecipesapp.data.model.FoodResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(
        @QueryMap queries: Map<String, String>
    ): Response<FoodResult>
}