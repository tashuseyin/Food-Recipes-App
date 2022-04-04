package com.tashuseyin.foodrecipesapp.presentation.ui.detail_activity.instructions

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.tashuseyin.foodrecipesapp.bindingadapter.BindingFragment
import com.tashuseyin.foodrecipesapp.databinding.FragmentInstructionsBinding

class InstructionsFragment : BindingFragment<FragmentInstructionsBinding>(){
    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentInstructionsBinding::inflate

}