package com.tashuseyin.foodrecipesapp.presentation.ui.detail_activity.ingredients

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.tashuseyin.foodrecipesapp.bindingadapter.BindingFragment
import com.tashuseyin.foodrecipesapp.databinding.FragmentIngredientsBinding


class IngredientsFragment : BindingFragment<FragmentIngredientsBinding>(){
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentIngredientsBinding::inflate
}