package com.example.quickbite.model

data class Recipe(
    val idMeal: String,
    val strMeal: String,
    val strMealAlternative: String = "",
    val strCategory: String? = null,
    val strArea: String? = null,
    val strInstructions: String? = null,
    val strMealThumb: String? = null,
    val strTags: List<String> = emptyList(),
    val strYoutube: String? = null,
    val strIngredients: List<String> = emptyList(),
    val strMeasures: List<String> = emptyList(),
    val strSource: String? = null,
    val strImageSource: String? = null,
    val strCreativeCommonsConfirmed: String? = null,
    val dateModified: String? = null

)

data class Video(
    val name: String,
    val key: String,
    val site: String = "YouTube",
    val url: String
)


