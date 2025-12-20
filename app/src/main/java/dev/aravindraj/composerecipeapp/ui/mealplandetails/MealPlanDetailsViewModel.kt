package dev.aravindraj.composerecipeapp.ui.mealplandetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aravindraj.composerecipeapp.data.model.mealplan.MealPlanDetails
import dev.aravindraj.composerecipeapp.data.repository.RecipeRepository
import dev.aravindraj.composerecipeapp.navigation.RecipeAppDestinationsArgs.MEAL_PLAN_ID_ARG
import dev.aravindraj.composerecipeapp.ui.base.UiState
import dev.aravindraj.composerecipeapp.utils.AppConstants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealPlanDetailsViewModel @Inject constructor(
    private val repository: RecipeRepository, savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mealPlanId: Int = checkNotNull(savedStateHandle[MEAL_PLAN_ID_ARG])
    private val _uiState = MutableStateFlow<UiState<MealPlanDetails>>(UiState.Loading)
    val uiState: StateFlow<UiState<MealPlanDetails>> = _uiState

    init {
        fetchMealPlanDetails()
    }

    fun fetchMealPlanDetails() {
        viewModelScope.launch {
            repository.getMealPlanDetails(
                AppConstants.getUserName(), mealPlanId, AppConstants.getUserNameHash()
            ).catch { e ->
                _uiState.value = UiState.Error(e.toString())
            }.collect { it -> _uiState.value = UiState.Success(it) }
        }
    }

}