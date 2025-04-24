package com.example.quickbite.screens

import com.example.quickbite.model.Recipe
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.JsonElement



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
