package com.devspace.myapplication.common.model

import com.devspace.myapplication.detail.data.RecipeDetail

data class RecipesDto(
    val id: Int,
    val title: String,
    val image: String,
    val summary: String
)

