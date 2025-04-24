package com.example.quickbite.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.remember
import com.example.quickbite.model.Recipe
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.JsonElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.quickbite.model.Video


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipe: Recipe,
    onBackClick: () -> Unit
) {
    var fullRecipe by remember { mutableStateOf<Recipe?>(null) }

    LaunchedEffect(recipe.idMeal) {
        fullRecipe = fetchRecipeById(recipe.idMeal)
    }

    if (fullRecipe == null) {
        Text("Loading...", modifier = Modifier.padding(16.dp))
        return
    }

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        item {
            TopAppBar(
                title = { Text(fullRecipe!!.strMeal) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }

        item {
            Text(
                text = fullRecipe!!.strMeal,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            fullRecipe!!.strMealThumb?.let {
                AsyncImage(
                    model = it,
                    contentDescription = fullRecipe!!.strMeal,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )
            }
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

        item {
            fullRecipe!!.strYoutube?.let { url ->
                val videoKey = url.substringAfter("v=")
                val video = Video(
                    name = "Watch on YouTube",
                    key = videoKey,
                    url = url
                )
                Spacer(modifier = Modifier.height(12.dp))
                VideoDisplay(video = video)
            }
        }

    }
}

suspend fun fetchRecipeById(id: String): Recipe? {
    return withContext(Dispatchers.IO) {
        try {
            val url = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=$id"
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val body = response.body?.string() ?: return@withContext null
            val json = Json.parseToJsonElement(body).jsonObject
            val meals = json["meals"]?.jsonArray ?: return@withContext null
            val recipeJson = meals.first().jsonObject
            parseRecipe(recipeJson)
        } catch (e: Exception) {
            null
        }
    }
}

fun parseRecipe(json: JsonObject): Recipe {
    val idMeal = json["idMeal"]?.jsonPrimitive?.content ?: ""
    val strMeal = json["strMeal"]?.jsonPrimitive?.content ?: ""
    val strMealAlternative = json["strMeal"]?.jsonPrimitive?.content ?: ""
    val strCategory = json["strCategory"]?.jsonPrimitive?.contentOrNull
    val strArea = json["strArea"]?.jsonPrimitive?.contentOrNull
    val strInstructions = json["strInstructions"]?.jsonPrimitive?.contentOrNull
    val strMealThumb = json["strMealThumb"]?.jsonPrimitive?.contentOrNull
    val strTagsRaw = json["strTags"]?.jsonPrimitive?.contentOrNull
    val strTags = strTagsRaw?.split(",")?.map { it.trim() } ?: emptyList()
    val strYoutube = json["strYoutube"]?.jsonPrimitive?.contentOrNull

    val ingredients = mutableListOf<String>()
    val measures = mutableListOf<String>()

    for (i in 1..20) {
        val ingredient = json["strIngredient$i"]?.jsonPrimitive?.contentOrNull?.trim()
        val measure = json["strMeasure$i"]?.jsonPrimitive?.contentOrNull?.trim()
        if (!ingredient.isNullOrBlank()) ingredients.add(ingredient)
        if (!measure.isNullOrBlank()) measures.add(measure)
    }

    return Recipe(
        idMeal = idMeal,
        strMeal = strMeal,
        strMealAlternative = strMealAlternative,
        strCategory = strCategory,
        strArea = strArea,
        strInstructions = strInstructions,
        strMealThumb = strMealThumb,
        strTags = strTags,
        strYoutube = strYoutube,
        strIngredients = ingredients,
        strMeasures = measures
    )
}
