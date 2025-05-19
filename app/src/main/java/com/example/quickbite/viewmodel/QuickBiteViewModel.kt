package com.example.quickbite.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quickbite.model.Recipe
import com.example.quickbite.model.toRecipe
import com.example.quickbite.network.RetrofitInstance
import com.example.quickbite.uistate.QuickBiteUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuickBiteViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(QuickBiteUIState())
    val uiState: StateFlow<QuickBiteUIState> = _uiState.asStateFlow()

    fun setSelectedRecipe(recipe: Recipe){
        _uiState.update { currentState ->
            currentState.copy(selectedRecipe = recipe)
        }
    }
    fun fetchRecipesByCategory(category: String) {
        viewModelScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.getRecipesByCategory(category)
                }
                val recipes = response.meals?.map { it.toRecipe() } ?: emptyList()
                _uiState.update { it.copy(recipeList = recipes) }
            } catch (e: Exception) {
                Log.e("QuickBiteViewModel", "Error fetching recipes for $category: ${e.message}")
            }
        }
    }

    fun fetchFullRecipeById(id: String) {
        viewModelScope.launch {
            try {
                val fullRecipe = withContext(Dispatchers.IO) {
                    RetrofitInstance.api.getRecipeById(id).meals?.firstOrNull()?.toRecipe()
                }
                fullRecipe?.let {
                    _uiState.update { it.copy(selectedRecipe = fullRecipe) }
                }
            } catch (e: Exception) {
                Log.e("QuickBiteViewModel", "Error fetching full recipe: ${e.message}")
            }
        }
    }
}

