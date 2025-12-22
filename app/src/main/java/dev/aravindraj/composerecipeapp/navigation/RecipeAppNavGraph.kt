package dev.aravindraj.composerecipeapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.aravindraj.composerecipeapp.navigation.RecipeAppDestinationsArgs.MEAL_PLAN_ID_ARG
import dev.aravindraj.composerecipeapp.navigation.RecipeAppDestinationsArgs.RECIPE_ID_ARG
import dev.aravindraj.composerecipeapp.ui.home.HomeViewModel
import dev.aravindraj.composerecipeapp.ui.main.MainScreen
import dev.aravindraj.composerecipeapp.ui.mealplandetails.MealPlanDetailsScreen
import dev.aravindraj.composerecipeapp.ui.recipebyingredients.RecipeByIngredientsScreen
import dev.aravindraj.composerecipeapp.ui.recipebyingredients.RecipeByIngredientsViewModel
import dev.aravindraj.composerecipeapp.ui.recipedetail.RecipeDetailsScreen

@Composable
fun RecipeAppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = RecipeAppDestinations.MAIN_ROUTE
) {
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: startDestination
    val navigationActions = RecipeAppNavigationActions(navController)
    val recipeByIngredientsViewModel: RecipeByIngredientsViewModel = hiltViewModel()


    NavHost(
        navController = navController, startDestination = startDestination, modifier = modifier
    ) {
        composable(
            RecipeAppDestinations.MAIN_ROUTE
        ) { backStackEntry ->
            val viewModel: HomeViewModel = hiltViewModel(backStackEntry)
            MainScreen(navController, viewModel, recipeByIngredientsViewModel)
        }
        composable(
            route = RecipeAppDestinations.RECIPE_DETAILS_ROUTE, arguments = listOf(
                navArgument(RECIPE_ID_ARG) {
                    type = NavType.IntType
                })
        ) { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(RecipeAppDestinations.MAIN_ROUTE)
            }
            val viewModel: HomeViewModel = hiltViewModel(parentEntry)
            val recipeItemId = backStackEntry.arguments?.getInt(RECIPE_ID_ARG)!!
            RecipeDetailsScreen(
                recipeId = recipeItemId, onNavigateBack = { navController.navigateUp() }, viewModel
            )
        }
        composable(RecipeAppDestinations.RECIPE_BY_INGREDIENTS_ROUTE) {
            RecipeByIngredientsScreen(recipeByIngredientsViewModel, onNavigateBack = {
                navController.navigateUp()
            })
        }
        composable(
            route = RecipeAppDestinations.MEAL_PLAN_DETAILS_ROUTE, arguments = listOf(
                navArgument(MEAL_PLAN_ID_ARG) {
                    type = NavType.IntType
                })
        ) {
            MealPlanDetailsScreen(
                mealPlanDetailsViewModel = hiltViewModel(),
                onNavigateBack = { navController.navigateUp() })
        }
    }

}