package com.tashuseyin.foodrecipesapp.data.model.remote

import com.google.gson.annotations.SerializedName


data class FoodResult(
    @SerializedName("results")
    val foodRecipesList: List<FoodRecipeDto>
)