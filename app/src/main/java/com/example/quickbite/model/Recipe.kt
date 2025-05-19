package com.example.quickbite.model

import com.google.gson.annotations.SerializedName


fun NetworkRecipe.toRecipe(): Recipe {
    val ingredients = listOf(
        strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
        strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
        strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15,
        strIngredient16, strIngredient17, strIngredient18, strIngredient19, strIngredient20
    ).filterNot { it.isNullOrBlank() }.map { it!! }

    val measures = listOf(
        strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
        strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10,
        strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15,
        strMeasure16, strMeasure17, strMeasure18, strMeasure19, strMeasure20
    ).filterNot { it.isNullOrBlank() }.map { it!! }


    val tags = strTagsRaw?.split(",")?.map { it.trim() } ?: emptyList()

    return Recipe(
        idMeal = idMeal,
        strMeal = strMeal,
        strMealAlternative = strMealAlternative ?: "",
        strCategory = strCategory,
        strArea = strArea,
        strInstructions = strInstructions,
        strMealThumb = strMealThumb,
        strTags = tags,
        strYoutube = strYoutube,
        strIngredients = ingredients,
        strMeasures = measures,
        strSource = strSource,
        strImageSource = strImageSource,
        strCreativeCommonsConfirmed = strCreativeCommonsConfirmed,
        dateModified = dateModified
    )
}


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


data class NetworkRecipe(
    @SerializedName("idMeal")
    val idMeal: String,

    @SerializedName("strMeal")
    val strMeal: String,

    @SerializedName("strMealAlternate")
    val strMealAlternative: String? = null,

    @SerializedName("strCategory")
    val strCategory: String? = null,

    @SerializedName("strArea")
    val strArea: String? = null,

    @SerializedName("strInstructions")
    val strInstructions: String? = null,

    @SerializedName("strMealThumb")
    val strMealThumb: String? = null,

    @SerializedName("strTags")
    val strTagsRaw: String? = null,

    @SerializedName("strYoutube")
    val strYoutube: String? = null,

    @SerializedName("strSource")
    val strSource: String? = null,

    @SerializedName("strImageSource")
    val strImageSource: String? = null,

    @SerializedName("strCreativeCommonsConfirmed")
    val strCreativeCommonsConfirmed: String? = null,

    @SerializedName("dateModified")
    val dateModified: String? = null,

    // Ingredients
    @SerializedName("strIngredient1") val strIngredient1: String? = null,
    @SerializedName("strIngredient2") val strIngredient2: String? = null,
    @SerializedName("strIngredient3") val strIngredient3: String? = null,
    @SerializedName("strIngredient4") val strIngredient4: String? = null,
    @SerializedName("strIngredient5") val strIngredient5: String? = null,
    @SerializedName("strIngredient6") val strIngredient6: String? = null,
    @SerializedName("strIngredient7") val strIngredient7: String? = null,
    @SerializedName("strIngredient8") val strIngredient8: String? = null,
    @SerializedName("strIngredient9") val strIngredient9: String? = null,
    @SerializedName("strIngredient10") val strIngredient10: String? = null,
    @SerializedName("strIngredient11") val strIngredient11: String? = null,
    @SerializedName("strIngredient12") val strIngredient12: String? = null,
    @SerializedName("strIngredient13") val strIngredient13: String? = null,
    @SerializedName("strIngredient14") val strIngredient14: String? = null,
    @SerializedName("strIngredient15") val strIngredient15: String? = null,
    @SerializedName("strIngredient16") val strIngredient16: String? = null,
    @SerializedName("strIngredient17") val strIngredient17: String? = null,
    @SerializedName("strIngredient18") val strIngredient18: String? = null,
    @SerializedName("strIngredient19") val strIngredient19: String? = null,
    @SerializedName("strIngredient20") val strIngredient20: String? = null,

    // Measures
    @SerializedName("strMeasure1") val strMeasure1: String? = null,
    @SerializedName("strMeasure2") val strMeasure2: String? = null,
    @SerializedName("strMeasure3") val strMeasure3: String? = null,
    @SerializedName("strMeasure4") val strMeasure4: String? = null,
    @SerializedName("strMeasure5") val strMeasure5: String? = null,
    @SerializedName("strMeasure6") val strMeasure6: String? = null,
    @SerializedName("strMeasure7") val strMeasure7: String? = null,
    @SerializedName("strMeasure8") val strMeasure8: String? = null,
    @SerializedName("strMeasure9") val strMeasure9: String? = null,
    @SerializedName("strMeasure10") val strMeasure10: String? = null,
    @SerializedName("strMeasure11") val strMeasure11: String? = null,
    @SerializedName("strMeasure12") val strMeasure12: String? = null,
    @SerializedName("strMeasure13") val strMeasure13: String? = null,
    @SerializedName("strMeasure14") val strMeasure14: String? = null,
    @SerializedName("strMeasure15") val strMeasure15: String? = null,
    @SerializedName("strMeasure16") val strMeasure16: String? = null,
    @SerializedName("strMeasure17") val strMeasure17: String? = null,
    @SerializedName("strMeasure18") val strMeasure18: String? = null,
    @SerializedName("strMeasure19") val strMeasure19: String? = null,
    @SerializedName("strMeasure20") val strMeasure20: String? = null
)

data class RecipeResponse(
    val meals: List<NetworkRecipe>?
)

