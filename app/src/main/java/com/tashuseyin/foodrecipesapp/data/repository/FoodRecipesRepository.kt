package com.tashuseyin.foodrecipesapp.data.repository

import com.tashuseyin.foodrecipesapp.data.datasource.remote.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class FoodRecipesRepository @Inject constructor(
    remoteDataSource: RemoteDataSource
) {
    val remote = remoteDataSource
}