package dev.aravindraj.composerecipeapp.data.source.remote

import dev.aravindraj.composerecipeapp.data.model.RandomRecipesResponse
import dev.aravindraj.composerecipeapp.data.model.RecipeByIngredients
import dev.aravindraj.composerecipeapp.data.model.mealplan.MealPlanDetails
import dev.aravindraj.composerecipeapp.data.model.mealplan.MealTemplatesResponse
import javax.inject.Inject


class RemoteDataSource @Inject constructor(private val recipeAPIService: RecipeAPIService) {

    suspend fun getRandomRecipes(number: Int): RandomRecipesResponse {
        return recipeAPIService.getRandomRecipes(number = number)
    }

    suspend fun getRecipesByIngredients(
        ingredients: String, number: Int
    ): List<RecipeByIngredients> {
        return recipeAPIService.getRecipesByIngredients(ingredients = ingredients, number = number)
    }

    suspend fun getMealTemplates(): MealTemplatesResponse {
        return recipeAPIService.getMealTemplates()
    }

    suspend fun getMealPlanDetails(
        userName: String,
        id: Int,
        userNameHash: String
    ): MealPlanDetails {
        return recipeAPIService.getMealPlanDetails(userName, id, userNameHash)
    }
}
