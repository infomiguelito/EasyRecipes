package com.devspace.myapplication

import android.app.Application
import androidx.room.Room
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.data.local.EasyRecipesDataBase
import com.devspace.myapplication.list.data.RecipesListRepository
import com.devspace.myapplication.list.data.local.RecipeListLocalDataSource
import com.devspace.myapplication.list.data.remote.ListService
import com.devspace.myapplication.list.data.remote.RecipeListRemoteDataSource

class  EasyRecipeApplication : Application(){
    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            EasyRecipesDataBase::class.java, "database-easy-recipes"
        ).build()
    }

    private val listRecipes by lazy {
        RetrofitClient.retrofitInstance.create(ListService::class.java)
    }

    private val localDataSource:RecipeListLocalDataSource by lazy {
        RecipeListLocalDataSource(db.getRecipeDao())
    }

    private val remoteDataSource: RecipeListRemoteDataSource by lazy {
        RecipeListRemoteDataSource(listRecipes)
    }

    val repository:RecipesListRepository by lazy {
        RecipesListRepository(
            local = localDataSource,
            remote = remoteDataSource,
        )
    }
}