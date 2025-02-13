package com.devspace.myapplication.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.EasyRecipeApplication
import com.devspace.myapplication.list.presentation.ui.RecipeListUiState
import com.devspace.myapplication.list.presentation.ui.RecipeUiData
import com.devspace.myapplication.list.data.RecipesListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class ListRecipesViewModel(
    private val repository: RecipesListRepository
) : ViewModel() {

    private val _uiRandom = MutableStateFlow(RecipeListUiState())
    val uiRandom: StateFlow<RecipeListUiState> = _uiRandom

    init {
        fetchGetRandom()
    }

    private fun fetchGetRandom() {
        _uiRandom.value = RecipeListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getRecipes()
            if (response.isSuccess) {
                val recipe = response.getOrNull()
                if (recipe != null) {
                    val recipeUiDataList = recipe.map { recipesDto ->
                        RecipeUiData(
                            id = recipesDto.id,
                            title = recipesDto.title,
                            image = recipesDto.image,
                            summary = recipesDto.summary,

                        )
                    }
                    _uiRandom.value = RecipeListUiState(list = recipeUiDataList)
                }
            } else {
                val ex = response.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiRandom.value = RecipeListUiState(
                        isError = true,
                        errorMessage = "Not internet connection"
                    )
                } else {
                    _uiRandom.value = RecipeListUiState(isError = true)
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return ListRecipesViewModel(
                    repository = (application as EasyRecipeApplication).repository
                ) as T
            }
        }
    }
}
