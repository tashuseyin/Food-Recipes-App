package com.tashuseyin.foodrecipesapp.baseviewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.tashuseyin.foodrecipesapp.common.Resource
import com.tashuseyin.foodrecipesapp.data.datasource.local.entity.RecipesEntity
import com.tashuseyin.foodrecipesapp.data.model.FoodRecipes
import com.tashuseyin.foodrecipesapp.data.model.FoodResult
import com.tashuseyin.foodrecipesapp.data.repository.FoodRecipesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val repository: FoodRecipesRepository
) : AndroidViewModel(application) {

    /**Database**/
    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readRecipes().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.local.insertRecipes(recipesEntity)
    }

    /**Retrofit**/
    private val _recipesResponse: MutableLiveData<Resource<FoodResult>> = MutableLiveData()
    val recipesResponse get() = _recipesResponse


    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        _recipesResponse.value = Resource.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getFoodRecipes(queries)
                _recipesResponse.value = handleResponse(response)

                val foodRecipe = recipesResponse.value!!.data
                if (foodRecipe != null){
                    offlineCacheRecipes(foodRecipe)
                }

            } catch (e: Exception) {
                _recipesResponse.value = Resource.Error("Recipes not found.")
            }
        } else {
            _recipesResponse.value = Resource.Error("No Internet Connection.")
        }
    }

    private fun handleResponse(response: Response<FoodResult>): Resource<FoodResult> {
        return when {
            response.message().toString().contains("timeout") -> {
                Resource.Error("Timeout")
            }
            response.code() == 402 -> {
                Resource.Error("API Key Limited")
            }
            response.body()!!.foodRecipesList.isNullOrEmpty() -> {
                Resource.Error("Recipes not found")
            }
            response.isSuccessful -> {
                val foodRecipesData = response.body()
                Resource.Success(foodRecipesData!!)
            }
            else -> {
                Resource.Error(response.message())
            }
        }
    }

    private fun offlineCacheRecipes(foodRecipe: FoodResult) {
        val recipesEntity = RecipesEntity(foodRecipe)
        insertRecipes(recipesEntity)
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


}
