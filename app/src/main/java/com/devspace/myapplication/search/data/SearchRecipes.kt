package com.devspace.myapplication.search.data

import com.devspace.myapplication.common.data.remote.model.SearchRecipesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRecipes {
    @GET("/recipes/complexSearch?")
    suspend fun searchRecipesQuery(@Query("query") query: String): Response<SearchRecipesResponse>
}