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

@Composable
fun MainScreen(navController: NavHostController, viewModel: HomeViewModel) {
    val navigationActions = RecipeAppNavigationActions(navController)
    val bottomNavItems = listOf(
        BottomNavItem.HomeItem
    )
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController, items = bottomNavItems)
        }) { paddingValues ->
        NavHost(
            navController = rememberNavController(),
            startDestination = RecipeAppScreens.HOME_SCREEN,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(RecipeAppDestinations.HOME_ROUTE) {
                HomeScreen(onRecipeClick = { recipeId ->
                    navigationActions.navigateToRecipeDetail(recipeId)
                }, viewModel)
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
                icon = { Icon(screen.icon, contentDescription = screen.label) },
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
