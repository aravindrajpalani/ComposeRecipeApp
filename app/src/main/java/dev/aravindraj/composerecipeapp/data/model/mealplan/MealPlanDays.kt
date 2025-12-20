package dev.aravindraj.composerecipeapp.data.model.mealplan

import com.google.gson.annotations.SerializedName

data class MealPlanDays(

    @SerializedName("nutritionSummary")
    var nutritionSummary: NutritionSummary? = NutritionSummary(),
    @SerializedName("nutritionSummaryBreakfast")
    var nutritionSummaryBreakfast: NutritionSummaryBreakfast? = NutritionSummaryBreakfast(),
    @SerializedName("nutritionSummaryLunch")
    var nutritionSummaryLunch: NutritionSummaryLunch? = NutritionSummaryLunch(),
    @SerializedName("nutritionSummaryDinner")
    var nutritionSummaryDinner: NutritionSummaryDinner? = NutritionSummaryDinner(),
    @SerializedName("day")
    var day: String? = null,
    @SerializedName("items")
    var items: ArrayList<NutrientItems> = arrayListOf()

)