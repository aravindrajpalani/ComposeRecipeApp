package dev.aravindraj.composerecipeapp.data.model.mealplan

import com.google.gson.annotations.SerializedName


data class MealPlanDetails(

    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("days")
    var days: ArrayList<MealPlanDays> = arrayListOf()

)