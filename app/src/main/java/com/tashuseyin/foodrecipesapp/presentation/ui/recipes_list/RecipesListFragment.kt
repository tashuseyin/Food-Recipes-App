package com.tashuseyin.foodrecipesapp.presentation.ui.recipes_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.tashuseyin.foodrecipesapp.baseviewmodel.MainViewModel
import com.tashuseyin.foodrecipesapp.common.Resource
import com.tashuseyin.foodrecipesapp.databinding.FragmentRecipesListBinding
import com.tashuseyin.foodrecipesapp.presentation.bindingadapter.BindingFragment
import com.tashuseyin.foodrecipesapp.presentation.ui.recipes_list.adapter.RecipesAdapter
import com.tashuseyin.foodrecipesapp.util.Util
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesListFragment : BindingFragment<FragmentRecipesListBinding>() {
    private val adapter by lazy { RecipesAdapter() }
    private val mainViewModel: MainViewModel by viewModels()
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentRecipesListBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        requestApiData()
    }

    private fun setRecyclerView() {
        binding.recyclerview.adapter = adapter
        showShimmerEffect()
    }

    private fun requestApiData() {
        mainViewModel.getRecipes(Util.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    hideShimmerEffect()
                    result.data.let { adapter.setData(it!!) }
                }
                is Resource.Error -> {
                    hideShimmerEffect()
                    binding.errorTextView.isVisible = true
                }
                is Resource.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun showShimmerEffect() {
        binding.shimmerLayout.startShimmer()
        binding.shimmerLayout.visibility = View.VISIBLE
        binding.recyclerview.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerLayout.stopShimmer()
        binding.shimmerLayout.visibility = View.GONE
        binding.recyclerview.visibility = View.VISIBLE
    }
}
