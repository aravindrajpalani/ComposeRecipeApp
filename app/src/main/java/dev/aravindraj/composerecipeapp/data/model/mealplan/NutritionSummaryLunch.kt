package dev.aravindraj.composerecipeapp.data.model.mealplan

import com.google.gson.annotations.SerializedName


data class NutritionSummaryLunch(

    @SerializedName("nutrients")
    var nutrients: ArrayList<Nutrients> = arrayListOf()

)