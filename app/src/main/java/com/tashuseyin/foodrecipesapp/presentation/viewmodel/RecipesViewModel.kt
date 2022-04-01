package com.tashuseyin.foodrecipesapp.presentation.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tashuseyin.foodrecipesapp.common.Constants
import com.tashuseyin.foodrecipesapp.common.Constants.API_KEY
import com.tashuseyin.foodrecipesapp.common.Constants.DEFAULT_DIET_TYPE
import com.tashuseyin.foodrecipesapp.common.Constants.DEFAULT_MEAL_TYPE
import com.tashuseyin.foodrecipesapp.common.Constants.DEFAULT_RECIPES_NUMBER
import com.tashuseyin.foodrecipesapp.common.Constants.QUERY_ADD_RECIPE_INFORMATION
import com.tashuseyin.foodrecipesapp.common.Constants.QUERY_API_KEY
import com.tashuseyin.foodrecipesapp.common.Constants.QUERY_FILL_INGREDIENTS
import com.tashuseyin.foodrecipesapp.common.Constants.QUERY_NUMBER
import com.tashuseyin.foodrecipesapp.common.Constants.QUERY_SEARCH
import com.tashuseyin.foodrecipesapp.data.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var networkStatus = false
    var backOnline = false

    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) =
        viewModelScope.launch {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }

    fun saveBackOnline(backOnline: Boolean) = viewModelScope.launch {
        dataStoreRepository.saveBackOnline(backOnline)
    }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[Constants.QUERY_NUMBER] = Constants.DEFAULT_RECIPES_NUMBER
        queries[Constants.QUERY_API_KEY] = Constants.API_KEY
        queries[Constants.QUERY_TYPE] = mealType
        queries[Constants.QUERY_DIET] = dietType
        queries[Constants.QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[Constants.QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }

    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }

    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (networkStatus) {
            if (backOnline) {
                Toast.makeText(getApplication(), "We're back online.", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }


}
