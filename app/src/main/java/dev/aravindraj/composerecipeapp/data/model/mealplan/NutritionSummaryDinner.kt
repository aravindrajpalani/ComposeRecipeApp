package dev.aravindraj.composerecipeapp.data.model.mealplan

import com.google.gson.annotations.SerializedName


data class NutritionSummaryDinner(

    @SerializedName("nutrients")
    var nutrients: ArrayList<Nutrients> = arrayListOf()

)