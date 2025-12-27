package com.abdul.mazaaditask.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.abdul.mazaaditask.ui.compose.launchdetail.LaunchDetailScreen
import com.abdul.mazaaditask.ui.compose.launches.LaunchesScreen

/**
 * Defines all app screens with their routes
 * Using sealed class for type-safe navigation
 */
sealed class Screen(val route: String) {
    object Launches : Screen("launches")
    object LaunchDetail : Screen("launch_detail/{id}") {
        /**
         * Helper function to create route with launch ID
         */
        fun createRoute(id: String) = "launch_detail/$id"
    }
}

/**
 * Navigation graph defining all screens and navigation flow
 * 
 * Navigation flow:
 * Launches Screen -> Launch Detail Screen (with launch ID)
 */
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Launches.route
    ) {
        // Launches list screen - entry point
        composable(Screen.Launches.route) {
            LaunchesScreen(
                onLaunchClick = { launchId ->
                    // Navigate to detail screen when user clicks a launch
                    navController.navigate(Screen.LaunchDetail.createRoute(launchId))
                }
            )
        }
        
        // Launch detail screen - requires launch ID parameter
        composable(
            route = Screen.LaunchDetail.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            // Extract launch ID from navigation arguments
            val launchId = backStackEntry.arguments?.getString("id") ?: ""
            LaunchDetailScreen(
                launchId = launchId,
                onBackClick = { 
                    // Navigate back to list screen
                    navController.popBackStack() 
                }
            )
        }
    }
}

