package dev.aravindraj.composerecipeapp.data.model

import com.google.gson.annotations.SerializedName

data class UsedIngredients(

    @SerializedName("aisle")
    var aisle: String? = null,
    @SerializedName("amount")
    var amount: Double? = null,
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("meta")
    var meta: ArrayList<String> = arrayListOf(),
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("original")
    var original: String? = null,
    @SerializedName("originalName")
    var originalName: String? = null,
    @SerializedName("unit")
    var unit: String? = null,
    @SerializedName("unitLong")
    var unitLong: String? = null,
    @SerializedName("unitShort")
    var unitShort: String? = null

)