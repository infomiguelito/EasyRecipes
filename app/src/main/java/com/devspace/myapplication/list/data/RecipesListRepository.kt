package com.devspace.myapplication.list.data

import android.accounts.NetworkErrorException
import com.devspace.myapplication.common.model.RecipesResponse
import com.devspace.myapplication.list.data.local.RecipeListLocalDataSource
import com.devspace.myapplication.list.data.remote.ListService
import com.devspace.myapplication.list.data.remote.RecipeListRemoteDataSource

class RecipesListRepository(
    private val local: RecipeListLocalDataSource,
    private val remote: RecipeListRemoteDataSource,
) {
    suspend fun getRecipes(): Result<RecipesResponse?> {
        return Result.success(RecipesResponse(emptyList()))
    }
        /*return try {
            val response = listService.getRandom()
            if (response.isSuccessful) {
                Result.success(response.body())
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }*/
}