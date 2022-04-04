package com.tashuseyin.foodrecipesapp.presentation.ui.detail_activity.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebViewClient
import androidx.viewbinding.ViewBinding
import com.tashuseyin.foodrecipesapp.bindingadapter.BindingFragment
import com.tashuseyin.foodrecipesapp.common.Constants
import com.tashuseyin.foodrecipesapp.data.model.FoodRecipes
import com.tashuseyin.foodrecipesapp.databinding.FragmentInstructionsBinding

class InstructionsFragment : BindingFragment<FragmentInstructionsBinding>(){
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentInstructionsBinding::inflate


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        val myBundle: FoodRecipes = args!!.getParcelable<FoodRecipes>(Constants.RECIPES_BUNDLE_KEY) as FoodRecipes

        binding.webView.webViewClient = object : WebViewClient(){}
        val websiteUrl = myBundle.sourceUrl
        binding.webView.loadUrl(websiteUrl)
    }
}