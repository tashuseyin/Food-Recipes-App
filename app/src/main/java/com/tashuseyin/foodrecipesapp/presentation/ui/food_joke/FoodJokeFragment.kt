package com.tashuseyin.foodrecipesapp.presentation.ui.food_joke

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.tashuseyin.foodrecipesapp.databinding.FragmentFoodJokeBinding
import com.tashuseyin.foodrecipesapp.bindingadapter.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodJokeFragment : BindingFragment<FragmentFoodJokeBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentFoodJokeBinding::inflate

}