package com.example.quickbite.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.zIndex
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.remember
import com.example.quickbite.model.Recipe
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.example.quickbite.model.Video
import com.example.quickbite.viewmodel.QuickBiteViewModel
import com.example.quickbite.screens.VideoDisplay



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    onBackClick: () -> Unit,
    viewModel: QuickBiteViewModel = viewModel()
) {
    val scrollState = rememberScrollState()
    val uiState by viewModel.uiState.collectAsState()
    val fullRecipe = uiState.selectedRecipe
    val context = LocalContext.current

    LaunchedEffect(recipe.idMeal) {
        viewModel.fetchFullRecipeById(recipe.idMeal)
    }

    if (fullRecipe == null) {
        Text("Loading...", modifier = Modifier.padding(16.dp))
        return
    }

    val video = fullRecipe.strYoutube?.let { ytUrl ->
        val videoKey = ytUrl.substringAfter("v=")
        Video(name = "Instructional Video", key = videoKey, url = ytUrl)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(fullRecipe.strMeal) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black // Black for contrast
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    navigationIconContentColor = Color.White
                ),
                modifier = Modifier
                    .zIndex(1f)
            )
        },
        containerColor = Color.Transparent,
        modifier = Modifier.background(MaterialTheme.colorScheme.background)
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
            item {
                Text(
                    text = fullRecipe.strMeal,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            item {
                AsyncImage(
                    model = recipe.strMealThumb,
                    contentDescription = recipe.strMeal,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            item {
                Text("Category: ${fullRecipe!!.strCategory ?: "N/A"}")
            }

            item {
                Text("Area: ${fullRecipe!!.strArea ?: "N/A"}")
            }

            item {
                Text("Ingredients:", style = MaterialTheme.typography.titleLarge)
            }

            // Pairs Ingredients and Measurements together
            items(fullRecipe!!.strIngredients.zip(fullRecipe!!.strMeasures)) { (ingredient, measure) ->
                Text("- $ingredient: $measure")
            }

            item {
                Text("Instructions:", style = MaterialTheme.typography.titleLarge)
            }

            item {
                Text(fullRecipe!!.strInstructions ?: "No instructions available")
            }

            item {
                Text(
                    text = "Video Instructions",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            video?.let {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    VideoDisplay(video = it)
                }
            }

        }
    }
}
