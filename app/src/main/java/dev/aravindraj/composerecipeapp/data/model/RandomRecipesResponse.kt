package dev.aravindraj.composerecipeapp.data.model

import com.google.gson.annotations.SerializedName

data class RandomRecipesResponse(
    @SerializedName("recipes")
    var recipes: ArrayList<Recipe> = arrayListOf()
)