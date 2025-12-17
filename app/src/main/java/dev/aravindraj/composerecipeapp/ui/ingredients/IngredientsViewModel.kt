package dev.aravindraj.composerecipeapp.ui.ingredients


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aravindraj.composerecipeapp.data.model.IngredientCategory
import dev.aravindraj.composerecipeapp.data.repository.RecipeRepository
import dev.aravindraj.composerecipeapp.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IngredientsViewModel @Inject constructor(private val repository: RecipeRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<IngredientCategory>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<IngredientCategory>>> = _uiState

    init {
        fetchAllIngredients()
    }

    fun fetchAllIngredients() {
        viewModelScope.launch {
            repository.getAllIngredients()
                .catch { e -> _uiState.value = UiState.Error(e.toString()) }
                .collect { it -> _uiState.value = UiState.Success(it) }
        }
    }

}