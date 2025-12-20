package dev.aravindraj.composerecipeapp.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.aravindraj.composerecipeapp.navigation.BottomNavItem
import dev.aravindraj.composerecipeapp.navigation.RecipeAppDestinations
import dev.aravindraj.composerecipeapp.navigation.RecipeAppNavigationActions
import dev.aravindraj.composerecipeapp.navigation.RecipeAppScreens
import dev.aravindraj.composerecipeapp.ui.home.HomeScreen
import dev.aravindraj.composerecipeapp.ui.home.HomeViewModel
import dev.aravindraj.composerecipeapp.ui.ingredients.IngredientsScreen
import dev.aravindraj.composerecipeapp.ui.mealplan.MealPlanScreen
import dev.aravindraj.composerecipeapp.ui.recipebyingredients.RecipeByIngredientsViewModel

@Composable
fun MainScreen(
    navControllerMain: NavHostController,
    homeViewModel: HomeViewModel,
    recipeByIngredientsViewModel: RecipeByIngredientsViewModel
) {
    val navigationActions = RecipeAppNavigationActions(navControllerMain)
    val bottomNavItems = listOf(
        BottomNavItem.HomeItem, BottomNavItem.IngredientsItem, BottomNavItem.MealPlanItem
    )
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, items = bottomNavItems)
        }) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = RecipeAppScreens.HOME_SCREEN,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(RecipeAppDestinations.HOME_ROUTE) {
                HomeScreen(onRecipeClick = { recipeId ->
                    navigationActions.navigateToRecipeDetails(recipeId)
                }, homeViewModel)
            }
            composable(RecipeAppDestinations.INGREDIENTS_ROUTE) {
                IngredientsScreen(
                    viewModel = hiltViewModel(), onNavigateToRecipes = { selectedIngredients ->
                        recipeByIngredientsViewModel.setSelectedIngredients(selectedIngredients)
                        navigationActions.navigateToRecipeByIngredients()
                        recipeByIngredientsViewModel.findRecipesByIngredients()
                    })
            }
            composable(RecipeAppDestinations.MEAL_PLAN_ROUTE) {
                MealPlanScreen(
                    onMealPlanClick = { mealPlanId ->
                        navigationActions.navigateToMealPlanDetails(
                            mealPlanId = mealPlanId
                        )
                    }, mealPlanViewModel = hiltViewModel()
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController, items: List<BottomNavItem>
) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(screen.icon), contentDescription = screen.label
                    )
                },
                label = { Text(screen.label) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}
