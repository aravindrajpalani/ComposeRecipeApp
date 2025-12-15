package dev.aravindraj.composerecipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import dev.aravindraj.composerecipeapp.navigation.RecipeAppNavGraph
import dev.aravindraj.composerecipeapp.ui.theme.ComposeRecipeAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeRecipeAppTheme {
                RecipeAppNavGraph()
            }
        }
    }
}