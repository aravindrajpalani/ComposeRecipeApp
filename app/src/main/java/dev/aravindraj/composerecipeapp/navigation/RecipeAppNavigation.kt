package dev.aravindraj.composerecipeapp.navigation

import androidx.navigation.NavHostController
import dev.aravindraj.composerecipeapp.R
import dev.aravindraj.composerecipeapp.navigation.RecipeAppDestinations.RECIPE_BY_INGREDIENTS_ROUTE
import dev.aravindraj.composerecipeapp.navigation.RecipeAppDestinationsArgs.RECIPE_ID_ARG
import dev.aravindraj.composerecipeapp.navigation.RecipeAppScreens.HOME_SCREEN
import dev.aravindraj.composerecipeapp.navigation.RecipeAppScreens.INGREDIENTS_SCREEN
import dev.aravindraj.composerecipeapp.navigation.RecipeAppScreens.MAIN_SCREEN
import dev.aravindraj.composerecipeapp.navigation.RecipeAppScreens.MEAL_PLAN_SCREEN
import dev.aravindraj.composerecipeapp.navigation.RecipeAppScreens.RECIPE_BY_INGREDIENTS_SCREEN
import dev.aravindraj.composerecipeapp.navigation.RecipeAppScreens.RECIPE_DETAILS_SCREEN

object RecipeAppScreens {
    const val MAIN_SCREEN = "main"
    const val HOME_SCREEN = "home"
    const val INGREDIENTS_SCREEN = "ingredients"
    const val MEAL_PLAN_SCREEN = "mealPlan"
    const val RECIPE_DETAILS_SCREEN = "recipeDetails"
    const val RECIPE_BY_INGREDIENTS_SCREEN = "recipeByIngredients"
}

object RecipeAppDestinationsArgs {
    const val RECIPE_ID_ARG = "recipeId"
}

object RecipeAppDestinations {
    const val MAIN_ROUTE = MAIN_SCREEN
    const val HOME_ROUTE = HOME_SCREEN
    const val INGREDIENTS_ROUTE = INGREDIENTS_SCREEN
    const val MEAL_PLAN_ROUTE = MEAL_PLAN_SCREEN
    const val RECIPE_DETAILS_ROUTE = "${RECIPE_DETAILS_SCREEN}/{${RECIPE_ID_ARG}}"
    const val RECIPE_BY_INGREDIENTS_ROUTE = RECIPE_BY_INGREDIENTS_SCREEN
}

sealed class BottomNavItem(val route: String, val icon: Int, val label: String) {
    object HomeItem : BottomNavItem(HOME_SCREEN, R.drawable.ic_home, "Home")
    object IngredientsItem :
        BottomNavItem(INGREDIENTS_SCREEN, R.drawable.ic_ingredients, "Ingredients")

    object MealPlanItem : BottomNavItem(MEAL_PLAN_SCREEN, R.drawable.ic_meal_plan, "Meal Plan")
}

class RecipeAppNavigationActions(private val navController: NavHostController) {
    fun navigateToRecipeDetail(recipeId: Int) {
        navController.navigate("${RECIPE_DETAILS_SCREEN}/$recipeId")
    }

    fun navigateToRecipeByIngredients() {
        navController.navigate(RECIPE_BY_INGREDIENTS_ROUTE)
    }
}