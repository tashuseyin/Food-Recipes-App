package com.tashuseyin.foodrecipesapp.presentation.ui.recipes_list.state

import com.tashuseyin.foodrecipesapp.data.model.remote.FoodRecipeDto

data class RecipesListState(
    val isLoading: Boolean = false,
    val coins: List<FoodRecipeDto> = emptyList(),
    val error: String = ""
)