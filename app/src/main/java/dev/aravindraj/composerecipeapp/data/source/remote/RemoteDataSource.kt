package dev.aravindraj.composerecipeapp.data.source.remote

import dev.aravindraj.composerecipeapp.data.model.RandomRecipesResponse
import javax.inject.Inject


class RemoteDataSource @Inject constructor(private val recipeAPIService: RecipeAPIService) {

    suspend fun getRandomRecipes(number: Int): RandomRecipesResponse {
        return recipeAPIService.getRandomRecipes(number = number)
    }

}
