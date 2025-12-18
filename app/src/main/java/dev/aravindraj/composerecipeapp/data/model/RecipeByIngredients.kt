package dev.aravindraj.composerecipeapp.data.model

import com.google.gson.annotations.SerializedName


data class RecipeByIngredients(

    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("imageType")
    var imageType: String? = null,
    @SerializedName("likes")
    var likes: Int? = null,
    @SerializedName("missedIngredientCount")
    var missedIngredientCount: Int? = null,
    @SerializedName("missedIngredients")
    var missedIngredients: ArrayList<MissedIngredients> = arrayListOf(),
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("unusedIngredients")
    var unusedIngredients: ArrayList<String> = arrayListOf(),
    @SerializedName("usedIngredientCount")
    var usedIngredientCount: Int? = null,
    @SerializedName("usedIngredients")
    var usedIngredients: ArrayList<UsedIngredients> = arrayListOf()

)