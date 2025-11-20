package com.example.test.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.test.ui.theme.TestTheme
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppNavigationTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    private lateinit var navController: TestNavHostController
    
    @Test
    fun appNavigation_startsAtHomeScreen() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            
            TestTheme {
                AppNavigation(navController = navController)
            }
        }
        
        // Verify we start at home screen
        assertEquals(Screen.Home.route, navController.currentBackStackEntry?.destination?.route)
        
        // Verify home screen content is displayed
        composeTestRule
            .onNodeWithText("Welcome to Test App")
            .assertIsDisplayed()
    }
    
    @Test
    fun appNavigation_navigatesToUsersScreen() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            
            TestTheme {
                AppNavigation(navController = navController)
            }
        }
        
        // Click "Go to Users" button
        composeTestRule
            .onNodeWithText("Go to Users")
            .performClick()
        
        // Verify navigation to users screen
        assertEquals(Screen.Users.route, navController.currentBackStackEntry?.destination?.route)
        
        // Verify users screen content is displayed
        composeTestRule
            .onNodeWithText("Users")
            .assertIsDisplayed()
    }
    
    @Test
    fun appNavigation_backNavigationFromUsersToHome() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            
            TestTheme {
                AppNavigation(navController = navController)
            }
        }
        
        // Navigate to users screen
        composeTestRule
            .onNodeWithText("Go to Users")
            .performClick()
        
        // Verify we're on users screen
        assertEquals(Screen.Users.route, navController.currentBackStackEntry?.destination?.route)
        
        // Click back button
        composeTestRule
            .onNodeWithContentDescription("Back")
            .performClick()
        
        // Verify we're back on home screen
        assertEquals(Screen.Home.route, navController.currentBackStackEntry?.destination?.route)
        
        composeTestRule
            .onNodeWithText("Welcome to Test App")
            .assertIsDisplayed()
    }
    
    @Test
    fun appNavigation_navigatesToProfileScreen() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            
            TestTheme {
                AppNavigation(navController = navController)
            }
        }
        
        // Navigate directly to profile screen with user ID 1
        navController.navigate(Screen.Profile.createRoute(1))
        
        // Verify navigation to profile screen
        assertEquals("profile/1", navController.currentBackStackEntry?.destination?.route)
        
        // Verify profile screen content is displayed
        composeTestRule
            .onNodeWithText("Profile")
            .assertIsDisplayed()
    }
    
    @Test
    fun appNavigation_backNavigationFromProfile() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            
            TestTheme {
                AppNavigation(navController = navController)
            }
        }
        
        // Navigate to users screen first
        composeTestRule
            .onNodeWithText("Go to Users")
            .performClick()
        
        // Then navigate to profile
        navController.navigate(Screen.Profile.createRoute(1))
        
        // Verify we're on profile screen
        assertEquals("profile/1", navController.currentBackStackEntry?.destination?.route)
        
        // Click back button
        composeTestRule
            .onNodeWithContentDescription("Back")
            .performClick()
        
        // Verify we're back on users screen
        assertEquals(Screen.Users.route, navController.currentBackStackEntry?.destination?.route)
        
        composeTestRule
            .onNodeWithText("Users")
            .assertIsDisplayed()
    }
}
