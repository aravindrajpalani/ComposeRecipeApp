package dev.aravindraj.composerecipeapp.data.model.mealplan

import com.google.gson.annotations.SerializedName
import dev.aravindraj.composerecipeapp.data.model.Value


data class NutrientItems(

    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("slot")
    var slot: Int,
    @SerializedName("position")
    var position: Int? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("value")
    var value: Value? = Value()

)