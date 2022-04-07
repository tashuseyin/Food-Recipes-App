package com.tashuseyin.foodrecipesapp.presentation.ui.main_activity.favorite_recipes

import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.tashuseyin.foodrecipesapp.R
import com.tashuseyin.foodrecipesapp.baseviewmodel.MainViewModel
import com.tashuseyin.foodrecipesapp.bindingadapter.BindingFragment
import com.tashuseyin.foodrecipesapp.databinding.FragmentFavoriteRecipesBinding
import com.tashuseyin.foodrecipesapp.presentation.ui.main_activity.favorite_recipes.adapter.FavoriteRecipesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteRecipesFragment : BindingFragment<FragmentFavoriteRecipesBinding>() {
    private lateinit var adapter: FavoriteRecipesAdapter
    private val mainViewModel: MainViewModel by viewModels()
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentFavoriteRecipesBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        adapter = FavoriteRecipesAdapter(requireActivity(), mainViewModel)
        binding.adapter = adapter
        binding.recyclerview.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.clearContextualActionMode()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorites_row_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all_favorite){
            mainViewModel.deleteAllFavoriteRecipes()
            showSnackBar()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showSnackBar(){
        Snackbar.make(binding.root, getString(R.string.all_delete), Snackbar.LENGTH_SHORT).setAction("Okay"){}.show()
    }

}
