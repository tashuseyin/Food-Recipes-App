package com.tashuseyin.foodrecipesapp.presentation.ui.detail_activity.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.tashuseyin.foodrecipesapp.R
import com.tashuseyin.foodrecipesapp.baseviewmodel.MainViewModel
import com.tashuseyin.foodrecipesapp.common.Constants.RECIPES_BUNDLE_KEY
import com.tashuseyin.foodrecipesapp.data.datasource.local.entity.FavoritesEntity
import com.tashuseyin.foodrecipesapp.databinding.ActivityDetailBinding
import com.tashuseyin.foodrecipesapp.presentation.ui.detail_activity.ingredients.IngredientsFragment
import com.tashuseyin.foodrecipesapp.presentation.ui.detail_activity.instructions.InstructionsFragment
import com.tashuseyin.foodrecipesapp.presentation.ui.detail_activity.overview.OverviewFragment
import com.tashuseyin.foodrecipesapp.presentation.ui.detail_activity.pager_adapter.PagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val args by navArgs<DetailActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()
    private var recipesSaved = false
    private var savedRecipeId = 0
    private lateinit var menuItem: MenuItem
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()
        setParameterPagerAdapter()
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setParameterPagerAdapter() {
        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPES_BUNDLE_KEY, args.result)

        val adapter = PagerAdapter(
            resultBundle,
            fragments,
            this
        )
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        menuItem = menu!!.findItem(R.id.save_to_favorite)
        checkedSavedRecipes(menuItem)
        return super.onCreateOptionsMenu(menu)
    }

    private fun checkedSavedRecipes(menuItem: MenuItem) {
        mainViewModel.readFavoriteRecipes.observe(this) { favoriteRecipes ->
            try {
                for (savedRecipe in favoriteRecipes) {
                    if (savedRecipe.result.id == args.result.id) {
                        changeMenuItemColor(menuItem, R.color.yellow)
                        savedRecipeId = savedRecipe.uid
                        recipesSaved = true
                    }
                }
            } catch (e: Exception) {
                Log.d("DetailsActivity", e.message.toString())
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (!(item.itemId == R.id.save_to_favorite && recipesSaved)) {
            saveToFavorites(item)
        } else if (item.itemId == R.id.save_to_favorite && recipesSaved) {
            removeFromFavorites(item)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun saveToFavorites(item: MenuItem) {
        val favoritesEntity =
            FavoritesEntity(
                0,
                args.result
            )
        mainViewModel.insertFavoriteRecipes(favoritesEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar(getString(R.string.saved_message))
        recipesSaved = true
    }

    private fun removeFromFavorites(item: MenuItem) {
        val favoritesEntity =
            FavoritesEntity(
                savedRecipeId,
                args.result
            )
        mainViewModel.deleteFavoriteRecipe(favoritesEntity)
        changeMenuItemColor(item, R.color.white)
        showSnackBar(getString(R.string.remove_message))
        recipesSaved = false

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            binding.detailsLayout,
            message,
            Snackbar.LENGTH_SHORT
        ).setAction("Okay") {}.show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }

    override fun onDestroy() {
        super.onDestroy()
        changeMenuItemColor(menuItem, R.color.white)
    }
}