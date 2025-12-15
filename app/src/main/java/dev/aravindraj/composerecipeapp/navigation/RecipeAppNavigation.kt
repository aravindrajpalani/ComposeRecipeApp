package dev.aravindraj.composerecipeapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import dev.aravindraj.composerecipeapp.navigation.RecipeAppScreens.HOME_SCREEN
import dev.aravindraj.composerecipeapp.navigation.RecipeAppScreens.MAIN_SCREEN

object RecipeAppScreens {
    const val MAIN_SCREEN = "main"
    const val HOME_SCREEN = "home"
}

object RecipeAppDestinationsArgs {

}

object RecipeAppDestinations {
    const val MAIN_ROUTE = MAIN_SCREEN
    const val HOME_ROUTE = HOME_SCREEN
}

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object HomeItem : BottomNavItem(HOME_SCREEN, Icons.Filled.Home, "Home")
}

class RecipeAppNavigationActions(private val navController: NavHostController) {

}