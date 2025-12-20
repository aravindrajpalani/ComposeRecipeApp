package dev.aravindraj.composerecipeapp.data.model.mealplan

import com.google.gson.annotations.SerializedName


data class NutritionSummaryBreakfast(

    @SerializedName("nutrients")
    var nutrients: ArrayList<Nutrients> = arrayListOf()

)