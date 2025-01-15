package com.devspace.myapplication

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("recipes/random?number=20")
    fun getRandom(): Call<RecipesDto>
}