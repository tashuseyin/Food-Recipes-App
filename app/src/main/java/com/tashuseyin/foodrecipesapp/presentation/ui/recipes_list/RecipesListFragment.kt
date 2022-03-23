package com.tashuseyin.foodrecipesapp.presentation.ui.recipes_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.tashuseyin.foodrecipesapp.R
import com.tashuseyin.foodrecipesapp.baseviewmodel.MainViewModel
import com.tashuseyin.foodrecipesapp.bindingadapter.BindingFragment
import com.tashuseyin.foodrecipesapp.common.Resource
import com.tashuseyin.foodrecipesapp.databinding.FragmentRecipesListBinding
import com.tashuseyin.foodrecipesapp.presentation.ui.recipes_list.adapter.RecipesAdapter
import com.tashuseyin.foodrecipesapp.util.Util
import com.tashuseyin.foodrecipesapp.util.observeOnce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesListFragment : BindingFragment<FragmentRecipesListBinding>() {
    private val adapter by lazy { RecipesAdapter() }
    private val mainViewModel: MainViewModel by viewModels()
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentRecipesListBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        setListener()
        setRecyclerView()
        readDatabase()
    }

    private fun setRecyclerView() {
        binding.recyclerview.adapter = adapter
        showShimmerEffect()
    }

    private fun setListener() {
        binding.apply {
            recipesFab.setOnClickListener {
                findNavController().navigate(R.id.action_recipesListFragment_to_recipesBottomSheetFragment)
            }
        }
    }


    private fun requestApiData() {
        mainViewModel.getRecipes(Util.applyQueries())
        /** observeOnce sayesinde uygulamayı ilk kez yüklenirken hem databaseden
        hem de apiden veri çeker bu durumu engellemek için kullanıdık**/
        mainViewModel.recipesResponse.observeOnce(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    hideShimmerEffect()
                    result.data.let { adapter.setData(it!!) }
                }
                is Resource.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    /** Eğer kullanıcı retrofitten bir hata alırsa databasedeki verileri göstersin istiyoruz. **/
                    binding.errorTextView.isVisible = true
                }
                is Resource.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    Log.d("TAG", "Database ")
                    adapter.setData(database[0].foodRecipe)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    adapter.setData(database[0].foodRecipe)
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
