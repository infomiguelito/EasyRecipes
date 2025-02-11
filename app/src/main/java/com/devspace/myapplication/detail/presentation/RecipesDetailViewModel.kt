package com.devspace.myapplication.detail.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.model.RecipesDto
import com.devspace.myapplication.detail.data.RecipeDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipesDetailViewModel(
    private val recipesDetail: RecipeDetail
) : ViewModel() {

    private val _uiRecipesDetail = MutableStateFlow<RecipesDto?>(null)
    val uiRecipesDetail: StateFlow<RecipesDto?> = _uiRecipesDetail

    @SuppressLint("SuspiciousIndentation")
    fun fetchRecipesDetail(id: String) {
        if (_uiRecipesDetail.value == null)
        viewModelScope.launch(Dispatchers.IO){
            val response = recipesDetail.getRecipeInformation(id)
            if (response.isSuccessful) {
                _uiRecipesDetail.value = response.body()
            } else {
                Log.d("RecipesDetailViewModel", "Request Error :: ${response.errorBody()}")
            }
        }
    }

    fun cleanRecipesId(){
        viewModelScope.launch {
            delay(1000)
            _uiRecipesDetail.value = null
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val recipesDetail = RetrofitClient.retrofitInstance.create(RecipeDetail::class.java)
                return RecipesDetailViewModel(
                    recipesDetail
                ) as T
            }
        }
    }
}

