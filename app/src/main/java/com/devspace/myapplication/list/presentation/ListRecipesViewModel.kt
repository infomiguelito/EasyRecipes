package com.devspace.myapplication.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.list.presentation.ui.RecipeListUiState
import com.devspace.myapplication.list.presentation.ui.RecipeUiData
import com.devspace.myapplication.list.data.ListRecipes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class ListRecipesViewModel(
    private val listRecipes: ListRecipes
) : ViewModel() {

    private val _uiRandom = MutableStateFlow<RecipeListUiState>(RecipeListUiState())
    val uiRandom: StateFlow<RecipeListUiState> = _uiRandom

    init {
        fetchGetRandom()
    }

    private fun fetchGetRandom() {
        _uiRandom.value = RecipeListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = listRecipes.getRandom()
                if (response.isSuccessful) {
                    val recipe = response.body()?.recipes
                    if (recipe != null){
                        val recipeUiDataList = recipe.map { recipesDto ->
                            RecipeUiData(
                                id = recipesDto.id,
                                image = recipesDto.image,
                                title = recipesDto.title,
                                summary = recipesDto.summary,
                            )

                        }
                        _uiRandom.value = RecipeListUiState(list = recipeUiDataList)
                    }
                } else {
                    _uiRandom.value = RecipeListUiState(isError = true)
                    Log.d("ListRecipesViewModel", "Request Error :: ${response.errorBody()}")
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                if (ex is UnknownHostException){
                    _uiRandom.value = RecipeListUiState(
                        isError = true,
                        errorMessage = "Not internet connection"
                    )

                }else{
                _uiRandom.value = RecipeListUiState(isError = true)
            }}
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val listRecipes = RetrofitClient.retrofitInstance.create(ListRecipes::class.java)
                return ListRecipesViewModel(
                    listRecipes
                ) as T
            }
        }
    }
}
