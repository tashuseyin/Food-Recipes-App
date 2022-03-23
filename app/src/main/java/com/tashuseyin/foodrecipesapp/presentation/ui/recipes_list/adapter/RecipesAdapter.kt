package com.tashuseyin.foodrecipesapp.presentation.ui.recipes_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tashuseyin.foodrecipesapp.data.model.FoodRecipes
import com.tashuseyin.foodrecipesapp.data.model.FoodResult
import com.tashuseyin.foodrecipesapp.databinding.RecipesRowLayoutBinding
import com.tashuseyin.foodrecipesapp.util.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesViewHolder>() {
    private var foodRecipesList = emptyList<FoodRecipes>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val binding =
            RecipesRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        holder.bind(foodRecipesList[position])
    }

    override fun getItemCount() = foodRecipesList.size

    fun setData(newData: FoodResult) {
        val recipesDiffUtil = RecipesDiffUtil(foodRecipesList, newData.foodRecipesList)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        foodRecipesList = newData.foodRecipesList
        diffUtilResult.dispatchUpdatesTo(this)
    }
}
