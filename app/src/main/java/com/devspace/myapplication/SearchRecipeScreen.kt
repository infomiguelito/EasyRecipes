package com.devspace.myapplication

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SearchRecipesScreen(query: String, navHostController: NavHostController) {

    val service = RetrofitClient.retrofitInstance.create(ApiService::class.java)
    var searchRecipes by rememberSaveable { mutableStateOf<List<SearchRecipeDto>>(emptyList()) }

    if(searchRecipes.isEmpty()){
        service.searchRecipes(query).enqueue(object : Callback<SearchRecipesResponse> {
            override fun onResponse(
                call: Call<SearchRecipesResponse>,
                response: Response<SearchRecipesResponse>
            ) {
                if (response.isSuccessful) {
                    searchRecipes = response.body()?.results ?: emptyList()
                } else {
                    Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<SearchRecipesResponse>, t: Throwable) {
                Log.d("MainActivity", "Network Error :: ${t.message}")
            }
        })
    }

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
                text = query
            )
        }
        SearchRecipesContent(recipes = searchRecipes, onClick = { itemClicked ->
            navHostController.navigate(route = "recipe_detail/${itemClicked.id}")
        })
    }

}

@Composable
private fun SearchRecipesContent(
    recipes: List<SearchRecipeDto>,
    onClick: (SearchRecipeDto) -> Unit
) {
    SearchRecipesList(recipes, onClick)
}

@Composable
fun SearchRecipesList(
    recipes: List<SearchRecipeDto>,
    onClick: (SearchRecipeDto) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ) {
        items(recipes) {
            SearchRecipeItem(searchRecipeDto = it, onClick = onClick)
        }
    }
}

@Composable
private fun SearchRecipeItem(
    searchRecipeDto: SearchRecipeDto,
    onClick: (SearchRecipeDto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClick.invoke(searchRecipeDto)
            }
    ) {

        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 8.dp , topStart = 8.dp))
                .fillMaxWidth()
                .height(150.dp),
            model = searchRecipeDto.image, contentDescription = "${searchRecipeDto.title} Image"
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            text = searchRecipeDto.title
        )
    }
}