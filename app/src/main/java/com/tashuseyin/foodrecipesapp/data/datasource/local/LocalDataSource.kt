package com.tashuseyin.foodrecipesapp.data.datasource.local

import com.tashuseyin.foodrecipesapp.data.datasource.local.db.RecipesDao
import com.tashuseyin.foodrecipesapp.data.datasource.local.entity.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipesDao: RecipesDao
) {
    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipes(recipesEntity)
    }

    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

}