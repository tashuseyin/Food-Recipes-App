package com.tashuseyin.foodrecipesapp.presentation.ui.recipes_list.adapter

import androidx.recyclerview.widget.RecyclerView
import com.tashuseyin.foodrecipesapp.data.model.FoodRecipes
import com.tashuseyin.foodrecipesapp.databinding.RecipesRowLayoutBinding

class RecipesViewHolder(
    private val binding: RecipesRowLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(foodRecipes: FoodRecipes) {
        binding.result = foodRecipes
        binding.executePendingBindings()
    }

}