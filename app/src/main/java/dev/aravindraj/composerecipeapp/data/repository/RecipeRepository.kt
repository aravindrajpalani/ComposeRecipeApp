package dev.aravindraj.composerecipeapp.data.repository

import dev.aravindraj.composerecipeapp.data.model.IngredientCategory
import dev.aravindraj.composerecipeapp.data.model.Recipe
import dev.aravindraj.composerecipeapp.data.model.RecipeByIngredients
import dev.aravindraj.composerecipeapp.data.source.local.LocalDataSource
import dev.aravindraj.composerecipeapp.data.source.remote.RemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource
) {

    fun getRandomRecipes(number: Int): Flow<List<Recipe>> {
        return flow { emit(remoteDataSource.getRandomRecipes(number)) }.map { it.recipes }
    }

    fun getAllIngredients(): Flow<List<IngredientCategory>> {
        return flow {
            emit(localDataSource.getAllIngredients())
        }.flowOn(Dispatchers.IO)
    }

    fun getRecipesByIngredients(ingredients: String, number: Int): Flow<List<RecipeByIngredients>> {
        return flow { emit(remoteDataSource.getRecipesByIngredients(ingredients, number)) }
    }

}