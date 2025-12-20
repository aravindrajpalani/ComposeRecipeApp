package dev.aravindraj.composerecipeapp.data.model.mealplan

import com.google.gson.annotations.SerializedName


data class MealTemplatesResponse(

    @SerializedName("templates")
    var templates: ArrayList<MealTemplate> = arrayListOf()

)