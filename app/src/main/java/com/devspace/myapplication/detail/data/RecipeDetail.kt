package com.devspace.myapplication.detail.data

import com.devspace.myapplication.common.data.remote.model.RecipesDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeDetail {

    @GET("recipes/{id}/information?includeNutrition=false")
    suspend fun getRecipeInformation(@Path("id") id: String): Response<RecipesDto>
}