package com.tashuseyin.foodrecipesapp.util

import androidx.recyclerview.widget.DiffUtil
import com.tashuseyin.foodrecipesapp.data.model.remote.FoodRecipeDto

class RecipesDiffUtil(
    private val oldList: List<FoodRecipeDto>,
    private val newList: List<FoodRecipeDto>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] === newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}