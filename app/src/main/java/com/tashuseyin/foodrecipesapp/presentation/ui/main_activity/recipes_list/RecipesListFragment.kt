package com.tashuseyin.foodrecipesapp.presentation.ui.main_activity.recipes_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.tashuseyin.foodrecipesapp.R
import com.tashuseyin.foodrecipesapp.baseviewmodel.MainViewModel
import com.tashuseyin.foodrecipesapp.bindingadapter.BindingFragment
import com.tashuseyin.foodrecipesapp.common.Resource
import com.tashuseyin.foodrecipesapp.databinding.FragmentRecipesListBinding
import com.tashuseyin.foodrecipesapp.presentation.ui.main_activity.recipes_list.adapter.RecipesAdapter
import com.tashuseyin.foodrecipesapp.presentation.viewmodel.RecipesViewModel
import com.tashuseyin.foodrecipesapp.util.NetworkListener
import com.tashuseyin.foodrecipesapp.util.observeOnce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesListFragment : BindingFragment<FragmentRecipesListBinding>(),
    SearchView.OnQueryTextListener {
    private val adapter by lazy { RecipesAdapter() }
    private val args: RecipesListFragmentArgs by navArgs()
    private val mainViewModel: MainViewModel by viewModels()
    private val recipesViewModel: RecipesViewModel by viewModels()
    private val networkListener = NetworkListener()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentRecipesListBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        setHasOptionsMenu(true)

        observeBackOnline()
        handleNetworkListener()
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
                if (recipesViewModel.networkStatus) {
                    findNavController().navigate(R.id.action_recipesListFragment_to_recipesBottomSheetFragment)
                } else {
                    recipesViewModel.showNetworkStatus()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

    private fun handleNetworkListener() {
        lifecycleScope.launch {
            networkListener.checkNetworkAvailable(requireContext())
                .collect { status ->
                    Log.d("NetworkListener", status.toString())
                    recipesViewModel.networkStatus = status
                    recipesViewModel.showNetworkStatus()
                }
        }
    }

    private fun observeBackOnline() {
        recipesViewModel.readBackOnline.observe(viewLifecycleOwner) {
            recipesViewModel.backOnline = it
        }
    }

    private fun searchApiData(searchQuery: String) {
        showShimmerEffect()
        mainViewModel.searchRecipes(recipesViewModel.applySearchQuery(searchQuery))
        mainViewModel.searchRecipesResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    hideShimmerEffect()
                    result.data.let { adapter.setData(it!!) }
                }
                is Resource.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(context, result.message.toString(), Toast.LENGTH_SHORT).show()
                    binding.errorTextView.isVisible = true
                }
                is Resource.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun requestApiData() {
        Log.d("RecipesFragment", "requestApiData called!")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        /** observeOnce sayesinde uygulamayı ilk kez yüklenirken hem databaseden
        hem de apiden veri çeker bu durumu engellemek için kullanıdık**/
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Success -> {
                    hideShimmerEffect()
                    result.data.let { adapter.setData(it!!) }
                }
                is Resource.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(context, result.message.toString(), Toast.LENGTH_SHORT).show()
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
                if (database.isNotEmpty() && !args.backBottomSheet) {
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
