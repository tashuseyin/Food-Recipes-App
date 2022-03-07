package com.tashuseyin.foodrecipesapp.data.model.local

import com.tashuseyin.foodrecipesapp.data.model.remote.ExtendedIngredient

data class FoodRecipes(
    val aggregateLikes: Int,
    val cheap: Boolean,
    val dairyFree: Boolean,
    val extendedIngredients: List<ExtendedIngredient>,
    val glutenFree: Boolean,
    val healthScore: Double,
    val id: Int,
    val image: String,
    val readyInMinutes: Int,
    val sourceName: String,
    val sourceUrl: String,
    val summary: String,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean
)
