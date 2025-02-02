package com.devspace.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devspace.myapplication.detail.presentation.RecipesDetailViewModel
import com.devspace.myapplication.list.presentation.ListRecipesViewModel
import com.devspace.myapplication.search.presentation.SearchRecipesViewModel
import com.devspace.myapplication.ui.theme.EasyRecipesTheme

class MainActivity : ComponentActivity() {

    private val listViewModel by viewModels<ListRecipesViewModel> { ListRecipesViewModel.Factory }
    private val detailViewModel by viewModels<RecipesDetailViewModel> {RecipesDetailViewModel.Factory}
    private val searchViewModel by viewModels<SearchRecipesViewModel> {SearchRecipesViewModel.Factory  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyRecipesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App(
                        listViewModel,
                        detailViewModel,
                        searchViewModel
                        )
                }
            }
        }
    }
}
