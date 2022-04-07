package com.tashuseyin.foodrecipesapp.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tashuseyin.foodrecipesapp.data.datasource.local.entity.FavoritesEntity
import com.tashuseyin.foodrecipesapp.presentation.ui.main_activity.favorite_recipes.adapter.FavoriteRecipesAdapter

class FavoriteRecipesBinding {
    companion object {

        @BindingAdapter("viewvisibility", "setData", requireAll = false)
        @JvmStatic
        fun setDataAndViewVisibility(
            view: View,
            favoritesEntity: List<FavoritesEntity>?,
            adapter: FavoriteRecipesAdapter?
        ) {

            if (favoritesEntity.isNullOrEmpty()) {
                when (view) {
                    is ImageView -> view.isVisible = true
                    is TextView -> view.isVisible = true
                    is RecyclerView -> view.isVisible = false
                }
            } else {
                when (view) {
                    is ImageView -> view.isVisible = false
                    is TextView -> view.isVisible = false
                    is RecyclerView -> {
                        view.isVisible = true
                        adapter?.setData(favoritesEntity)
                    }
                }
            }
        }
    }
}