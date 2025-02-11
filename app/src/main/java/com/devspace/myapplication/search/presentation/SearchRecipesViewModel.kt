package com.devspace.myapplication.search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.model.SearchRecipeDto
import com.devspace.myapplication.search.data.SearchRecipes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchRecipesViewModel(
    private val searchRecipes: SearchRecipes
) : ViewModel() {

    private val _uiSearchRecipes = MutableStateFlow<List<SearchRecipeDto>>(emptyList())
    val uiSearchRecipes: StateFlow<List<SearchRecipeDto>> = _uiSearchRecipes

    fun fetchSearchRecipes(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = searchRecipes.searchRecipesQuery(query)
            if (response.isSuccessful) {
                _uiSearchRecipes.value = (response.body()?.results ?: emptyList())
            } else {
                Log.d("MainActivity", "Request Error :: ${response.errorBody()}")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val searchRecipes =
                    RetrofitClient.retrofitInstance.create(SearchRecipes::class.java)
                return SearchRecipesViewModel(
                    searchRecipes
                ) as T
            }
        }
    }
}