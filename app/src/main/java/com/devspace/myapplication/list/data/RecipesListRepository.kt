package com.devspace.myapplication.list.data

import android.accounts.NetworkErrorException
import com.devspace.myapplication.common.data.model.Recipe
import com.devspace.myapplication.common.data.remote.model.RecipesResponse
import com.devspace.myapplication.list.data.local.RecipeListLocalDataSource
import com.devspace.myapplication.list.data.remote.ListService
import com.devspace.myapplication.list.data.remote.RecipeListRemoteDataSource

class RecipesListRepository(
    private val local: RecipeListLocalDataSource,
    private val remote: RecipeListRemoteDataSource,
) {
    suspend fun getRecipes(): Result<List<Recipe>?> {
        return try {
            val result = remote.getRecipes()
            if (result.isSuccess){
                val recipesRemote = result.getOrNull() ?: emptyList()
                if (recipesRemote.isNotEmpty()){
                    local.updateLocalItems(recipesRemote)
                }
                return Result.success(local.getRecipes())
            }else{
                val localData = local.getRecipes()
                if (localData.isEmpty()) {
                    return result
                }else {
                    Result.success(localData)
                }
            }



        } catch (ex: Exception) {
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}