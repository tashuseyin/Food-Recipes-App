package com.tashuseyin.foodrecipesapp.presentation.ui.detail_activity.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.tashuseyin.foodrecipesapp.bindingadapter.BindingFragment
import com.tashuseyin.foodrecipesapp.common.Constants.RECIPES_BUNDLE_KEY
import com.tashuseyin.foodrecipesapp.data.model.FoodRecipes
import com.tashuseyin.foodrecipesapp.databinding.FragmentIngredientsBinding
import com.tashuseyin.foodrecipesapp.presentation.ui.detail_activity.ingredients.adapter.IngredientsAdapter


class IngredientsFragment : BindingFragment<FragmentIngredientsBinding>() {
    private val adapter = IngredientsAdapter()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentIngredientsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val myBundle: FoodRecipes = args!!.getParcelable<FoodRecipes>(RECIPES_BUNDLE_KEY) as FoodRecipes

        binding.recyclerview.adapter = adapter
        adapter.setData(myBundle.extendedIngredients)
    }
}