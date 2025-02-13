package com.devspace.myapplication.list.data.local

import com.devspace.myapplication.common.data.local.RecipeCategory
import com.devspace.myapplication.common.data.local.RecipeDao
import com.devspace.myapplication.common.data.local.RecipeEntity
import com.devspace.myapplication.common.data.model.Recipe

class RecipeListLocalDataSource(
    private val dao: RecipeDao
) {


    suspend fun getRecipes(): List<Recipe>{
        val recipeEntity = dao.getAllRecipes(RecipeCategory.Recipes.name)

       return recipeEntity.map {
            Recipe(
                id = it.id,
                title = it.title,
                image = it.image,
                summary = it.summary,
                category = it.category,
            )
        }
    }

    suspend fun updateLocalItems(recipes: List<Recipe>){
        val entities = recipes.map {
            RecipeEntity(
                id = it.id,
                title = it.title,
                summary = it.summary,
                image = it.image,
                category = it.category
            )
        }
        dao.insertAll(entities)
    }
}