package dev.aravindraj.composerecipeapp.data.model

import com.google.gson.annotations.SerializedName

data class MealTemplate(

    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String? = null

)