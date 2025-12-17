package dev.aravindraj.composerecipeapp.navigation

import androidx.navigation.NavHostController
import dev.aravindraj.composerecipeapp.R
import dev.aravindraj.composerecipeapp.navigation.RecipeAppDestinationsArgs.RECIPE_ID_ARG
import dev.aravindraj.composerecipeapp.navigation.RecipeAppScreens.HOME_SCREEN
import dev.aravindraj.composerecipeapp.navigation.RecipeAppScreens.INGREDIENTS_SCREEN
import dev.aravindraj.composerecipeapp.navigation.RecipeAppScreens.MAIN_SCREEN
import dev.aravindraj.composerecipeapp.navigation.RecipeAppScreens.RECIPE_DETAILS_SCREEN

object RecipeAppScreens {
    const val MAIN_SCREEN = "main"
    const val HOME_SCREEN = "home"
    const val INGREDIENTS_SCREEN = "ingredients"
    const val RECIPE_DETAILS_SCREEN = "recipeDetails"
}

object RecipeAppDestinationsArgs {
    const val RECIPE_ID_ARG = "recipeId"
}

object RecipeAppDestinations {
    const val MAIN_ROUTE = MAIN_SCREEN
    const val HOME_ROUTE = HOME_SCREEN
    const val INGREDIENTS_ROUTE = INGREDIENTS_SCREEN
    const val RECIPE_DETAILS_ROUTE = "${RECIPE_DETAILS_SCREEN}/{${RECIPE_ID_ARG}}"
}

sealed class BottomNavItem(val route: String, val icon: Int, val label: String) {
    object HomeItem : BottomNavItem(HOME_SCREEN, R.drawable.ic_home, "Home")
    object IngredientsItem :
        BottomNavItem(INGREDIENTS_SCREEN, R.drawable.ic_ingredients, "Ingredients")
}

class RecipeAppNavigationActions(private val navController: NavHostController) {
    fun navigateToRecipeDetail(recipeId: Int) {
        navController.navigate("${RECIPE_DETAILS_SCREEN}/$recipeId")
    }
}