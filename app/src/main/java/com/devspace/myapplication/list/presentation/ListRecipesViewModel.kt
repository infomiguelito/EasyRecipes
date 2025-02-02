package com.devspace.myapplication.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.model.RecipesDto
import com.devspace.myapplication.common.model.RecipesResponse
import com.devspace.myapplication.list.data.ListRecipes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class ListRecipesViewModel(
    listRecipes: ListRecipes
) : ViewModel() {

    private val _uiRandom = MutableStateFlow<List<RecipesDto>>(emptyList())
    val uiRandom: StateFlow<List<RecipesDto>> = _uiRandom

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listRecipes.getRandom()
            if (response.isSuccessful) {
                _uiRandom.value = response.body()?.recipes ?: emptyList()
            } else {
                Log.d("ListRecipesViewModel", "Request Error :: ${response.errorBody()}")
            }
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
