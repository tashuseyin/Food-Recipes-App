package com.tashuseyin.foodrecipesapp.presentation.ui.detail_activity.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import coil.load
import com.tashuseyin.foodrecipesapp.R
import com.tashuseyin.foodrecipesapp.bindingadapter.BindingFragment
import com.tashuseyin.foodrecipesapp.bindingadapter.RecipesRowBinding
import com.tashuseyin.foodrecipesapp.data.model.FoodRecipes
import com.tashuseyin.foodrecipesapp.databinding.FragmentOverviewBinding


class OverviewFragment : BindingFragment<FragmentOverviewBinding>() {
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentOverviewBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadDesignView()
    }

    private fun loadDesignView() {
        val args = arguments
        val myBundle: FoodRecipes = args!!.getParcelable<FoodRecipes>("recipeBundle") as FoodRecipes

        binding.apply {
            mainImageView.load(myBundle.image)
            title.text = myBundle.title
            likesTextView.text = myBundle.aggregateLikes.toString()
            timeTextView.text = myBundle.readyInMinutes.toString()
            summaryText.text = myBundle.summary
            RecipesRowBinding.parseHtml(binding.summaryText, myBundle.summary)

            checkTagControl(myBundle.vegan, veganCheckView, veganText)
            checkTagControl(myBundle.vegetarian, vegetarianCheckView, vegetarianText)
            checkTagControl(myBundle.dairyFree, dairyFreeCheckView, dairyFreeText)
            checkTagControl(myBundle.glutenFree, glutenFreeCheckView, glutenFreeText)
            checkTagControl(myBundle.veryHealthy, healthyCheckView, healthyText)
            checkTagControl(myBundle.cheap, cheapCheckView, cheapText)
        }
    }

    private fun checkTagControl(
        status: Boolean,
        imageView: ImageView,
        textView: TextView
    ) {
        if (status) {
            imageView.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            textView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
    }


}