package dev.aravindraj.composerecipeapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.aravindraj.composerecipeapp.ui.home.HomeViewModel
import dev.aravindraj.composerecipeapp.ui.main.MainScreen

@Composable
fun RecipeAppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = RecipeAppDestinations.MAIN_ROUTE
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination
    val navigationActions = RecipeAppNavigationActions(navController)

    NavHost(
        navController = navController, startDestination = startDestination, modifier = modifier
    ) {
        composable(
            RecipeAppDestinations.MAIN_ROUTE
        ) { backStackEntry ->
            val viewModel: HomeViewModel = hiltViewModel(backStackEntry)
            MainScreen(navController, viewModel)
        }
    }

}