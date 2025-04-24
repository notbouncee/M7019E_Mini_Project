@file:kotlin.OptIn(ExperimentalMaterial3Api::class)
@file:Suppress("INFERRED_TYPE_VARIABLE_INTO_EMPTY_INTERSECTION_WARNING")

package com.example.quickbite.screens


import android.content.ContentValues.TAG
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quickbite.R
import com.example.quickbite.model.Recipe
import com.example.quickbite.utils.Constants
import com.example.quickbite.viewmodel.QuickBiteViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.withContext


enum class HomeScreen(@StringRes val title: Int){
    List(title = R.string.app_name),
    Detail(title = R.string.app_detail)
}


@Composable
fun TheQuickBiteApp(viewModel: QuickBiteViewModel = viewModel()) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    var filteredRecipes by remember { mutableStateOf<List<Recipe>>(emptyList()) }
    var selectedCategory by remember { mutableStateOf("Starter") }

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            filteredRecipes = fetchFilteredRecipes("Starter")
        }
    }

    fun loadCategory(category: String) {
        selectedCategory = category
        scope.launch {
            val result = withContext(Dispatchers.IO) {
                fetchFilteredRecipes(category)
            }
            filteredRecipes = result
        }
    }

    NavHost(
        navController = navController,
        startDestination = HomeScreen.List.name,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(HomeScreen.List.name) {
            RecipeListScreen(
                filteredRecipes = filteredRecipes,
                selectedCategory = selectedCategory,
                onRecipeClick = { recipe ->
                    viewModel.setSelectedRecipe(recipe)  //  viewModel handles selection
                    navController.navigate(HomeScreen.Detail.name)
                },
                onCategorySelected = { category ->
                    Log.d("CATEGORY_SELECTED", "User selected category: $category")
                    loadCategory(category)
                }
            )
        }
        composable(HomeScreen.Detail.name) {
            val recipe = viewModel.uiState.collectAsState().value.selectedRecipe
            if (recipe != null) {
                RecipeDetailScreen(
                    recipe = recipe,
                    onBackClick = { navController.popBackStack() }
                )
            } else {
                Text(
                    text = "Recipe not found",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

suspend fun fetchFilteredRecipes(category: String): List<Recipe> {
    val url = "https://www.themealdb.com/api/json/v1/${Constants.API_KEY}/filter.php?c=${category}"
    Log.d("FETCH_RECIPES", "Fetching for category: $category")

    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()

    return try {
        val response = client.newCall(request).execute()
        val json = response.body?.string() ?: return emptyList()
        Log.d("FETCH_RECIPES", "Response: $json")

        val jsonObject = JSONObject(json)
        val mealsArray = jsonObject.optJSONArray("meals") ?: return emptyList()

        val recipes = mutableListOf<Recipe>()
        for (i in 0 until mealsArray.length()) {
            val meal = mealsArray.getJSONObject(i)
            recipes.add(
                Recipe(
                    idMeal = meal.getString("idMeal"),
                    strMeal = meal.getString("strMeal"),
                    strMealThumb = meal.getString("strMealThumb")
                )
            )
        }
        Log.d("FETCH_RECIPES", "Fetched ${recipes.size} recipes")
        recipes
    } catch (e: Exception) {
        Log.e("FETCH_ERROR", "Error: ${e.message}")
        emptyList()
    }
}

