package com.tashuseyin.foodrecipesapp.presentation.ui.detail_activity.ingredients.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.tashuseyin.foodrecipesapp.R
import com.tashuseyin.foodrecipesapp.common.Constants
import com.tashuseyin.foodrecipesapp.data.model.ExtendedIngredient
import com.tashuseyin.foodrecipesapp.databinding.IngredientsRowLayoutBinding
import com.tashuseyin.foodrecipesapp.util.RecipesDiffUtil

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class IngredientsViewHolder(private val binding: IngredientsRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredient: ExtendedIngredient) {
            binding.apply {
                ingredientsImageView.load(Constants.BASE_IMAGE_URL + ingredient.image){
                    crossfade(600)
                    error(R.drawable.ic_error_palceholder)
                }
                ingredientsName.text = ingredient.name
                ingredientsAmount.text = ingredient.amount.toString()
                ingredientsUnit.text = ingredient.unit
                ingredientsConsistency.text = ingredient.consistency
                ingredientsOriginal.text = ingredient.original
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val binding =
            IngredientsRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        holder.bind(ingredientsList[position])
    }

    override fun getItemCount() = ingredientsList.size


    fun setData(ingredients: List<ExtendedIngredient>) {
        val recipesDiffUtil = RecipesDiffUtil(ingredientsList, ingredients)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        ingredientsList = ingredients
        diffUtilResult.dispatchUpdatesTo(this)
    }
}