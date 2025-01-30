package com.devspace.myapplication.detail.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.ApiService
import com.devspace.myapplication.common.model.RecipesDto
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.components.ERHtlmText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RecipeDetailScreen(id: String, navHostController: NavHostController) {

    val service = RetrofitClient.retrofitInstance.create(ApiService::class.java)
    var recipeDto by remember { mutableStateOf<RecipesDto?>(null) }

    service.getRecipeInformation(id).enqueue(object : Callback<RecipesDto> {
        override fun onResponse(call: Call<RecipesDto>, response: Response<RecipesDto>) {
            if (response.isSuccessful) {
                recipeDto = response.body()
            } else {
                Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
            }
        }

        override fun onFailure(call: Call<RecipesDto>, t: Throwable) {
            Log.d("MainActivity", "Network Error :: ${t.message}")
        }
    })

    recipeDto?.let {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navHostController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back Button"
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = it.title
                )
            }
            RecipeDetailContent(it)
        }
    }
}


@Composable
private fun RecipeDetailContent(recipe: RecipesDto) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .height(200.dp)
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            model = recipe.image,
            contentDescription = "${recipe.title} image"
        )

        ERHtlmText(
            text = recipe.summary,
        )

    }
}