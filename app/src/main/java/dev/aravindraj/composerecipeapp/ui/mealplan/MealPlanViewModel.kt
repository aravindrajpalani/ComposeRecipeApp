package dev.aravindraj.composerecipeapp.ui.mealplan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aravindraj.composerecipeapp.data.model.MealTemplate
import dev.aravindraj.composerecipeapp.data.repository.RecipeRepository
import dev.aravindraj.composerecipeapp.ui.base.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealPlanViewModel @Inject constructor(private val repository: RecipeRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<MealTemplate>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<MealTemplate>>> = _uiState

    init {
        fetchMealTemplates()
    }

    fun fetchMealTemplates() {
        viewModelScope.launch {
            repository.getMealTemplates()
                .catch { e -> _uiState.value = UiState.Error(e.toString()) }
                .collect { it -> _uiState.value = UiState.Success(it) }
        }
    }
}