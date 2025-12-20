package dev.aravindraj.composerecipeapp.data.source.remote

import dev.aravindraj.composerecipeapp.data.model.RandomRecipesResponse
import dev.aravindraj.composerecipeapp.data.model.RecipeByIngredients
import dev.aravindraj.composerecipeapp.data.model.mealplan.MealPlanDetails
import dev.aravindraj.composerecipeapp.data.model.mealplan.MealTemplatesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RecipeAPIService {

    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("number") number: Int
    ): RandomRecipesResponse

    @GET("recipes/findByIngredients")
    suspend fun getRecipesByIngredients(
        @Query("ingredients") ingredients: String, @Query("number") number: Int
    ): List<RecipeByIngredients>

    @GET("mealplanner/public-templates")
    suspend fun getMealTemplates(): MealTemplatesResponse

    @GET("mealplanner/{username}/templates/{id}")
    suspend fun getMealPlanDetails(
        @Path("username") userName: String,
        @Path("id") id: Int,
        @Query("hash") userNameHash: String
    ): MealPlanDetails
}