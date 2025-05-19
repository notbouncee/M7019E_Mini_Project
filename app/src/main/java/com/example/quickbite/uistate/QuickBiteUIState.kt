package com.example.quickbite.uistate

import com.example.quickbite.model.Recipe

data class QuickBiteUIState (
    val selectedRecipe: Recipe? = null,
    val recipeList: List<Recipe> = emptyList(),
    val errorMessage: String? = null
)
