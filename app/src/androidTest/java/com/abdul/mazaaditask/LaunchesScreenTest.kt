package com.abdul.mazaaditask

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.abdul.mazaaditask.presentation.navigation.NavGraph
import com.abdul.mazaaditask.ui.compose.launches.LaunchesScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class LaunchesScreenTest {
    
    @get:Rule
    val hiltRule = HiltAndroidRule(this)
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    private lateinit var navController: TestNavHostController
    
    @Before
    fun setup() {
        hiltRule.inject()
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    }
    
    @Test
    fun launchesScreen_displaysTitle() {
        composeTestRule.setContent {
            LaunchesScreen(onLaunchClick = {})
        }
        
        composeTestRule.onNodeWithText("Launches").assertExists()
    }
}

