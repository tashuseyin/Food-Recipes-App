package com.tashuseyin.foodrecipesapp.presentation.ui.recipes_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.tashuseyin.foodrecipesapp.baseviewmodel.MainViewModel
import com.tashuseyin.foodrecipesapp.databinding.FragmentRecipesListBinding
import com.tashuseyin.foodrecipesapp.presentation.bindingadapter.BindingFragment
import com.tashuseyin.foodrecipesapp.util.Util
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesListFragment : BindingFragment<FragmentRecipesListBinding>() {
    private val mainViewModel: MainViewModel by viewModels()
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentRecipesListBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun requestApiData() {
        mainViewModel.getRecipes(Util.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { result ->
            when(result){
            }
        }
    }

    private fun showShimmerEffect() {
        binding.shimmerLayout.showShimmer(true)
    }

    private fun hideShimmerEffect() {
        binding.shimmerLayout.hideShimmer()
    }
}
