package com.tashuseyin.foodrecipesapp.presentation.ui.main_activity.food_joke

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.tashuseyin.foodrecipesapp.R
import com.tashuseyin.foodrecipesapp.baseviewmodel.MainViewModel
import com.tashuseyin.foodrecipesapp.bindingadapter.BindingFragment
import com.tashuseyin.foodrecipesapp.common.Constants.API_KEY
import com.tashuseyin.foodrecipesapp.common.Resource
import com.tashuseyin.foodrecipesapp.databinding.FragmentFoodJokeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodJokeFragment : BindingFragment<FragmentFoodJokeBinding>() {
    private val mainViewModel: MainViewModel by viewModels()
    private var foodJoke = "No Food Joke"

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentFoodJokeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel
        setHasOptionsMenu(true)
        requestApi()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.food_joke_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share){
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT, foodJoke)
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun requestApi() {
        mainViewModel.getFoodJoke(API_KEY)
        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    binding.foodJokeText.text = response.data?.text
                    if (response.data != null){
                        foodJoke = response.data.text
                    }
                }
                is Resource.Error -> {
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                    Log.d("FoodJokeFragment", "Loading")
                }
            }
        }
    }

    private fun loadDataFromCache() {
        mainViewModel.readFoodJoke.observe(viewLifecycleOwner) { database ->
            if (database.isNotEmpty()) {
                binding.foodJokeText.text = database[0].foodJoke.text
                foodJoke = database[0].foodJoke.text
            }
        }
    }
}
