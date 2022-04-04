package com.tashuseyin.foodrecipesapp.presentation.ui.main_activity.recipes_list.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.tashuseyin.foodrecipesapp.common.Constants.DEFAULT_DIET_TYPE
import com.tashuseyin.foodrecipesapp.common.Constants.DEFAULT_MEAL_TYPE
import com.tashuseyin.foodrecipesapp.databinding.RecipesBottomSheetBinding
import com.tashuseyin.foodrecipesapp.presentation.viewmodel.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RecipesBottomSheetFragment : BottomSheetDialogFragment() {
    private val recipesViewModel: RecipesViewModel by viewModels()
    private var mealTypeChip = DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietTypeChip = DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    private var _binding: RecipesBottomSheetBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RecipesBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        readDataStore()
        setListener()
    }

    private fun setListener() {
        binding.apply {
            mealTypeChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
                val chip = group.findViewById<Chip>(selectedChipId)
                val selectedMealType = chip.text.toString().lowercase(Locale.ROOT)
                mealTypeChip = selectedMealType
                mealTypeChipId = selectedChipId
            }
            dietTypeChipGroup.setOnCheckedChangeListener { group, selectedChipId ->
                val chip = group.findViewById<Chip>(selectedChipId)
                val selectedDietType = chip.text.toString().lowercase(Locale.ROOT)
                dietTypeChip = selectedDietType
                dietTypeChipId = selectedChipId
            }
            applyBtn.setOnClickListener {
                recipesViewModel.saveMealAndDietType(
                    mealTypeChip,
                    mealTypeChipId,
                    dietTypeChip,
                    dietTypeChipId
                )
                findNavController().navigate(
                    RecipesBottomSheetFragmentDirections.actionRecipesBottomSheetFragmentToRecipesListFragment(
                        true
                    )
                )
            }
        }
    }

    private fun readDataStore() {
        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner) { value ->
            mealTypeChip = value.selectedMealType
            dietTypeChip = value.selectedDietType
            updateChip(value.selectedMealTypeId, binding.mealTypeChipGroup)
            updateChip(value.selectedDietTypeId, binding.dietTypeChipGroup)
        }
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                Log.d("RecipesBottomSheet", e.message.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}