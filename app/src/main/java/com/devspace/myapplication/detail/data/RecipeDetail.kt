package com.devspace.myapplication.detail.data

import com.devspace.myapplication.common.model.RecipesDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeDetail {
    @GET("recipes/{id}/information?includeNutrition=false")
    fun getRecipeInformation(@Path("id") id: String): Call<RecipesDto>
}