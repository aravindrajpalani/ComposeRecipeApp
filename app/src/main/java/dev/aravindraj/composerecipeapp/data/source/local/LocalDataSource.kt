package dev.aravindraj.composerecipeapp.data.source.local

import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.aravindraj.composerecipeapp.data.model.IngredientCategory
import dev.aravindraj.composerecipeapp.utils.readJson
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val assetManager: AssetManager,
    private val gson: Gson
) {

    fun getAllIngredients(): List<IngredientCategory> {
        val json = assetManager.readJson("ingredients.json")
        val type = object : TypeToken<List<IngredientCategory>>() {}.type
        return gson.fromJson(json, type)
    }
}
