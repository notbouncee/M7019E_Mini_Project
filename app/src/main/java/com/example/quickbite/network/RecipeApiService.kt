package com.example.quickbite.network

import com.example.quickbite.model.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {
    @GET("filter.php")
    suspend fun getRecipesByCategory(@Query("c") category: String): RecipeResponse

    @GET("lookup.php")
    suspend fun getRecipeById(@Query("i") id: String): RecipeResponse
}