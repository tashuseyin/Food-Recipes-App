package com.tashuseyin.foodrecipesapp.presentation.ui.detail_activity.pager_adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(
    private val resultBundle: Bundle,
    private val fragments: ArrayList<Fragment>,
    private val fm: FragmentActivity,
) : FragmentStateAdapter(fm) {

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {
        fragments[position].arguments = resultBundle
        return fragments[position]
    }
}