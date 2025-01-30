package com.devspace.myapplication.list.data

import com.devspace.myapplication.common.model.RecipesResponse
import retrofit2.Call
import retrofit2.http.GET

interface ListRecipes {
    @GET("recipes/random?number=20")
    fun getRandom(): Call<RecipesResponse>
}