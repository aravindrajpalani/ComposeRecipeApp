package dev.aravindraj.composerecipeapp.ui.recipebyingredients

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aravindraj.composerecipeapp.data.model.RecipeByIngredients
import dev.aravindraj.composerecipeapp.data.repository.RecipeRepository
import dev.aravindraj.composerecipeapp.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeByIngredientsViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _selectedIngredients = MutableStateFlow<List<String>>(emptyList())
    val selectedIngredients: StateFlow<List<String>> = _selectedIngredients

    private val _recipesUiState =
        MutableStateFlow<UiState<List<RecipeByIngredients>>>(UiState.Loading)
    val recipesUiState: StateFlow<UiState<List<RecipeByIngredients>>> = _recipesUiState

    fun setSelectedIngredients(ingredients: List<String>) {
        _selectedIngredients.value = ingredients
    }

    fun findRecipesByIngredients() {
        if (_selectedIngredients.value.isEmpty()) return

        viewModelScope.launch {
            _recipesUiState.value = UiState.Loading
            repository.getRecipesByIngredients(
                _selectedIngredients.value.joinToString(),
                number = 10
            )
                .catch { e -> _recipesUiState.value = UiState.Error(e.toString()) }
                .collect { recipes -> _recipesUiState.value = UiState.Success(recipes) }
        }
    }
}