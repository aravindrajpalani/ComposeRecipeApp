package dev.aravindraj.composerecipeapp.data.repository

import dev.aravindraj.composerecipeapp.data.model.Recipe
import dev.aravindraj.composerecipeapp.data.source.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    fun getRandomRecipes(number: Int): Flow<List<Recipe>> {
        return flow { emit(remoteDataSource.getRandomRecipes(number)) }.map { it.recipes }
    }
}