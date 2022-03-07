package com.tashuseyin.foodrecipesapp.presentation.ui.favorite_recipes

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.tashuseyin.foodrecipesapp.databinding.FragmentFavoriteRecipesBinding
import com.tashuseyin.foodrecipesapp.presentation.bindingadapter.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : BindingFragment<FragmentFavoriteRecipesBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentFavoriteRecipesBinding::inflate
}