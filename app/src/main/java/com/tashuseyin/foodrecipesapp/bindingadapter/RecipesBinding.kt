package com.tashuseyin.foodrecipesapp.bindingadapter

import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.tashuseyin.foodrecipesapp.common.Resource
import com.tashuseyin.foodrecipesapp.data.datasource.local.entity.RecipesEntity
import com.tashuseyin.foodrecipesapp.data.model.FoodResult

class RecipesBinding {

    companion object {

        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun errorImageVisibility(
            imageView: ImageView,
            apiResponse: Resource<FoodResult>?,
            database: List<RecipesEntity>?
        ) {
            if (apiResponse is Resource.Error && database.isNullOrEmpty()) {
                imageView.isVisible = true
            } else if (apiResponse is Resource.Loading) {
                imageView.isVisible = false
            } else if (apiResponse is Resource.Success) {
                imageView.isVisible = false
            }
        }

        @BindingAdapter("readApiResponse2", "readDatabase2", requireAll = true)
        @JvmStatic
        fun errorTextVisibility(
            textView: TextView,
            apiResponse: Resource<FoodResult>?,
            database: List<RecipesEntity>?
        ) {
            if (apiResponse is Resource.Error && database.isNullOrEmpty()) {
                textView.isVisible = true
                textView.text = apiResponse.message.toString()
            } else if (apiResponse is Resource.Loading) {
                textView.isVisible = false
            } else if (apiResponse is Resource.Success) {
                textView.isVisible = false
            }
        }
    }
}