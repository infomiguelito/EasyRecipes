package com.devspace.myapplication

data class RecipesResponse(
    val recipe: List<RecipesDto>
)
    data class RecipesDto(
        val id: Int,
        val title: String,
        val image: String,
        val summary: String
    )
