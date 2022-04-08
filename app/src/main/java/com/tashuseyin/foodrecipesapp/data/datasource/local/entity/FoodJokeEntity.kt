package com.tashuseyin.foodrecipesapp.data.datasource.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tashuseyin.foodrecipesapp.common.Constants.FOOD_JOKE_TABLE
import com.tashuseyin.foodrecipesapp.data.model.FoodJoke

@Entity(tableName = FOOD_JOKE_TABLE)
class FoodJokeEntity(
    @Embedded
    var foodJoke: FoodJoke
) {
    @PrimaryKey(autoGenerate = false)
    var uid: Int = 0
}

