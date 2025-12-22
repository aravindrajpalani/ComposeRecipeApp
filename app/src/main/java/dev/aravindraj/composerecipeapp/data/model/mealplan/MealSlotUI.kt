package dev.aravindraj.composerecipeapp.data.model.mealplan

data class MealSlotUI(
    val recipes: List<NutrientItems>,
    val products: List<NutrientItems>,
    val ingredients: List<NutrientItems>
)
