package dev.aravindraj.composerecipeapp.data.source.remote

import dev.aravindraj.composerecipeapp.data.model.RandomRecipesResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface RecipeAPIService {

    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") number: Int
    ): RandomRecipesResponse

}