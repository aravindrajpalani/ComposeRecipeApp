package dev.aravindraj.composerecipeapp.data.model.mealplan

data class MealPlanDayUI(
    val day: String,
    val nutritionSummary: NutritionSummaryUI,
    val breakfastSummary: NutritionSummaryUI,
    val lunchSummary: NutritionSummaryUI,
    val dinnerSummary: NutritionSummaryUI,
    val breakfastSlotUI: MealSlotUI,
    val lunchSlotUI: MealSlotUI,
    val dinnerSlotUI: MealSlotUI,
    val items: List<NutrientItems>
)
