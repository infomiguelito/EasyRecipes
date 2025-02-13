package com.devspace.myapplication.list.data.remote

import com.devspace.myapplication.common.data.remote.model.RecipesResponse
import retrofit2.Response
import retrofit2.http.GET

interface ListService {
    @GET("recipes/random?number=20")
    suspend fun getRandom(): Response<RecipesResponse>
}