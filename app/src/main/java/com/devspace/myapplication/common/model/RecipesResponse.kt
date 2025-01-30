package com.devspace.myapplication.common.model

data class RecipesResponse(
    val recipes: List<RecipesDto>
)
    data class RecipesDto(
        val id: Int,
        val title: String,
        val image: String,
        val summary: String
    )
