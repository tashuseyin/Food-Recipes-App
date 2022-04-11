package com.tashuseyin.foodrecipesapp.bindingadapter

import android.view.View
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
        fun handleReadDataErrors(
            view: View,
            apiResponse: Resource<FoodResult>?,
            database: List<RecipesEntity>?
        ) {

            when(view){
                is ImageView -> {
                    view.isVisible = apiResponse is Resource.Error && database.isNullOrEmpty()
                }

                is TextView -> {
                    view.isVisible = apiResponse is Resource.Error && database.isNullOrEmpty()
                    view.text = apiResponse?.message.toString()
                }
            }
        }
    }
}