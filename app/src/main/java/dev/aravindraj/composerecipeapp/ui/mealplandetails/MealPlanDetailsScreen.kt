package dev.aravindraj.composerecipeapp.ui.mealplandetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dev.aravindraj.composerecipeapp.ui.base.UiState
import dev.aravindraj.composerecipeapp.ui.components.ErrorScreen
import dev.aravindraj.composerecipeapp.ui.components.LoadingScreen


@Composable
fun MealPlanDetailsScreen(
    mealPlanDetailsViewModel: MealPlanDetailsViewModel = hiltViewModel(), onNavigateBack: () -> Unit
) {

    val uiState by mealPlanDetailsViewModel.uiState.collectAsState()


    when (val state = uiState) {
        is UiState.Loading -> {
            LoadingScreen()
        }

        is UiState.Success -> {

        }

        is UiState.Error -> {
            ErrorScreen("") { }
        }
    }


}













