package com.tashuseyin.foodrecipesapp.presentation.ui.detail_activity.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.tashuseyin.foodrecipesapp.R
import com.tashuseyin.foodrecipesapp.common.Constants.RECIPES_BUNDLE_KEY
import com.tashuseyin.foodrecipesapp.databinding.ActivityDetailBinding
import com.tashuseyin.foodrecipesapp.presentation.ui.detail_activity.ingredients.IngredientsFragment
import com.tashuseyin.foodrecipesapp.presentation.ui.detail_activity.instructions.InstructionsFragment
import com.tashuseyin.foodrecipesapp.presentation.ui.detail_activity.overview.OverviewFragment
import com.tashuseyin.foodrecipesapp.presentation.ui.detail_activity.pager_adapter.PagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private val args by navArgs<DetailActivityArgs>()
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
}