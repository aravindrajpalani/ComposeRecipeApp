package dev.aravindraj.composerecipeapp.ui.mealplandetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aravindraj.composerecipeapp.data.model.mealplan.MealPlanDay
import dev.aravindraj.composerecipeapp.data.model.mealplan.MealPlanDayUI
import dev.aravindraj.composerecipeapp.data.model.mealplan.MealPlanDetails
import dev.aravindraj.composerecipeapp.data.model.mealplan.MealSlotUI
import dev.aravindraj.composerecipeapp.data.model.mealplan.NutrientItems
import dev.aravindraj.composerecipeapp.data.model.mealplan.Nutrients
import dev.aravindraj.composerecipeapp.data.model.mealplan.NutritionSummaryUI
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
    private val _selectedDayIndex = MutableStateFlow(0)
    val selectedDayIndex: StateFlow<Int> = _selectedDayIndex

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

    fun selectDay(index: Int) {
        _selectedDayIndex.value = index
    }

    fun buildNutritionSummary(nutrients: List<Nutrients>?): NutritionSummaryUI {
        return NutritionSummaryUI(
            calories = nutrients.byName("calories"),
            protein = nutrients.byName("protein"),
            carbs = nutrients.byName("carbohydrates"),
            fat = nutrients.byName("fat")
        )
    }

    fun buildMealPlanDayUI(mealPlanDay: MealPlanDay): MealPlanDayUI {
        return MealPlanDayUI(
            day = mealPlanDay.day.orEmpty(),
            nutritionSummary = buildNutritionSummary(mealPlanDay.nutritionSummary?.nutrients),
            breakfastSummary = buildNutritionSummary(mealPlanDay.nutritionSummaryBreakfast?.nutrients),
            lunchSummary = buildNutritionSummary(mealPlanDay.nutritionSummaryLunch?.nutrients),
            dinnerSummary = buildNutritionSummary(mealPlanDay.nutritionSummaryDinner?.nutrients),
            breakfastSlotUI = buildMealSlotItems(mealPlanDay.items, MealSlot.BREAKFAST),
            lunchSlotUI = buildMealSlotItems(mealPlanDay.items, MealSlot.LUNCH),
            dinnerSlotUI = buildMealSlotItems(mealPlanDay.items, MealSlot.DINNER),
            items = mealPlanDay.items
        )
    }

    fun buildMealSlotItems(
        items: List<NutrientItems>, slot: MealSlot
    ): MealSlotUI {

        val slotItems = items.filter { it.slot == slot.slot }

        return MealSlotUI(
            recipes = slotItems.filter { it.type == MealItemType.RECIPE.value },
            products = slotItems.filter { it.type == MealItemType.PRODUCT.value },
            ingredients = slotItems.filter { it.type == MealItemType.INGREDIENTS.value })

    }


    fun List<Nutrients>?.byName(name: String): Nutrients? =
        this?.firstOrNull { it.name.equals(name, ignoreCase = true) }


}