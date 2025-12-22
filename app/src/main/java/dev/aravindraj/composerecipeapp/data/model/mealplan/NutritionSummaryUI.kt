package dev.aravindraj.composerecipeapp.data.model.mealplan

data class NutritionSummaryUI(
    val calories: Nutrients?,
    val protein: Nutrients?,
    val carbs: Nutrients?,
    val fat: Nutrients?
) {
    fun asList(): List<Nutrients?> =
        listOf(calories, carbs, protein, fat)
}
