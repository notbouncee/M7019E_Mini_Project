package com.example.quickbite.viewmodel

import androidx.lifecycle.ViewModel
import com.example.quickbite.model.Recipe
import com.example.quickbite.uistate.QuickBiteUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class QuickBiteViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(QuickBiteUIState())
    val uiState: StateFlow<QuickBiteUIState> = _uiState.asStateFlow()

    fun setSelectedRecipe(recipe: Recipe){
        _uiState.update { currentState ->
            currentState.copy(selectedRecipe = recipe)
        }
    }
}

