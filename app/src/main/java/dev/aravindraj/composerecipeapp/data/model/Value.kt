package dev.aravindraj.composerecipeapp.data.model

import com.google.gson.annotations.SerializedName


data class Value(

    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("imageType")
    var imageType: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("servings")
    var servings: String? = null,
    @SerializedName("ingredients")
    var ingredients: ArrayList<Ingredients> = arrayListOf()
)