package com.abdul.mazaaditask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.abdul.mazaaditask.presentation.navigation.NavGraph
import com.abdul.mazaaditask.ui.theme.MazaadiTaskTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity - Entry point of the application
 * 
 * Responsibilities:
 * - Sets up Compose UI
 * - Initializes Navigation
 * - Applies Material 3 theme
 * 
 * @AndroidEntryPoint enables Hilt dependency injection
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Apply Material 3 theme
            MazaadiTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Initialize navigation controller
                    val navController = rememberNavController()
                    // Set up navigation graph
                    NavGraph(navController = navController)
                }
            }
        }
    }
}